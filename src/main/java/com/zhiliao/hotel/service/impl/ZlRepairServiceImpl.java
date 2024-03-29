package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderToPhpVO;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderVO;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlRepairorderDTO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.OrderLogService;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlRepairServiceImpl implements ZlRepairService {

    private static final Logger logger = LoggerFactory.getLogger(ZlRepairServiceImpl.class);

    @Autowired
    private ZlRepairorderMapper zlRepairorderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Override
    public Map<String, Object> addRepairMsg(Long userid, RepairParam repairParam) {

        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userid);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        Map<String, Object> map = new HashMap<>();

        Integer now = DateUtils.javaToPhpNowDateTime();
        String serialNumber = OrderIDUtil.createOrderID("WX");
        ZlRepairorder zlRepairorder = new ZlRepairorder();
        zlRepairorder.setUserid(userid);
        zlRepairorder.setUsername(zlWxuserdetail.getRealname());
        if (StringUtils.isNoneBlank(zlWxuserdetail.getTel())) {
            zlRepairorder.setTel(zlWxuserdetail.getTel());
        }
        zlRepairorder.setHotelname(repairParam.getHotelname());  // 酒店名
        zlRepairorder.setHotelid(repairParam.getHotelid());     // 酒店ID
        zlRepairorder.setRoomid(repairParam.getRoomid());       // 客房ID
        zlRepairorder.setRoomnumber(repairParam.getRoomnumber());   //客房号
        zlRepairorder.setRemark(repairParam.getRemark());       // 备注信息
        zlRepairorder.setSerialnumber(serialNumber);  //订单号
        zlRepairorder.setCreatedate(now);
        zlRepairorder.setUpdatedate(now);
        zlRepairorder.setImgurls(repairParam.getImgurls());

        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(repairParam.getRoomnumber(), repairParam.getHotelid());
        if (zlHotelroom == null) {
            throw new BizException("您的码是前台码，不提供该服务");
        }
        zlRepairorder.setFloornumber(zlHotelroom.getRoomfloor());
        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userid);
        ZlWxuser wxuser = zlWxuserMapper.selectOne(zlWxuser);
        zlRepairorder.setUsername(wxuser.getNickname());
        zlRepairorderMapper.insertSelective(zlRepairorder);

        map.put("orderid", zlRepairorder.getOrderid());
        map.put("serialnumber", zlRepairorder.getSerialnumber());

        logger.info("报修订单插入数据库完成,订单id:" + zlRepairorder.getOrderid());
        // 推送消息
        RepairOrderToPhpVO repairOrderToPhpVO = zlRepairorderMapper.selectToPhp(zlRepairorder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_FACILITY, repairOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_REPAIR_ORDER);
        orderPhpSendVO.setMessage(repairOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_REPAIR_ORDER, orderStr);
        logger.info("推送报修订单到redis通知php后台人员完成,订单信息:" + repairOrderToPhpVO);

        return map;
    }


    @Override
    public ZlRepairorderDTO findRepairOrder(Long orderID) {
        ZlRepairorderDTO zlRepairorderDTO = zlRepairorderMapper.queryRepairMsg(orderID);
        List<String> imageurllist = new LinkedList<>();
        if (StringUtils.isNoneBlank(zlRepairorderDTO.getImgurls())) {
            String imageurls = zlRepairorderDTO.getImgurls();
            if (imageurls.contains("|")) {
                String[] imageurlArr = imageurls.split("\\|");
                for (int i = 0; i < imageurlArr.length; i++) {
                    imageurllist.add(imageurlArr[i]);
                }
            } else {
                imageurllist.add(imageurls);
            }
        }
        zlRepairorderDTO.setImageurllist(imageurllist);
        return zlRepairorderDTO;
    }

    @Override
    public void cancelRepairOrder(Long orderID) {

        Integer updatedate = DateUtils.javaToPhpNowDateTime();

        ZlRepairorder zlRepairorder = new ZlRepairorder();
        zlRepairorder.setOrderid(orderID);
        zlRepairorder = zlRepairorderMapper.selectOne(zlRepairorder);
        if (zlRepairorder == null) {
            throw new BizException("参数有误!");
        }

        //将订单取消操作写到记录表中
        orderLogService.cancelOrderLog(zlRepairorder.getOrderid(), zlRepairorder.getHotelid(), zlRepairorder.getCreatedate(), zlRepairorder.getMoldtype());
        zlRepairorderMapper.removeRepairOrder(orderID, updatedate);
    }

    /**
     * 用户删除报修订单
     *
     * @param orderID
     */
    @Override
    public void userDeleteRepairOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlRepairorderMapper.userDeleteRepairOrder(orderID, updateDate);
    }
}
