package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.clean.cleanparm.CleanParm;
import com.zhiliao.hotel.controller.clean.vo.CleanOrderToPhpVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.mapper.ZlCleanOrderMapper;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.PushInfoToPhpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCleanOrderServiceImpl implements ZlCleanOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ZlCleanOrderServiceImpl.class);

    @Autowired
    private ZlCleanOrderMapper zlCleanOrderMapper;

    @Autowired
    ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Override
    public Map<String, Object> addCleanOrder(Long userid, CleanParm cleanParm) {
        Map<String, Object> map = new HashMap<>();
//        ZlWxuserdetail zlWxuserdetail = zlWxuserdetailMapper.findByUserId(userid);
        String serialnumber = OrderIDUtil.createOrderID("QS");
        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setUserid(userid);   //用户ID  根据token获取userId
        zlCleanOrder.setSerialnumber(serialnumber);   //订单编号
//        zlCleanOrder.setUsername(zlWxuserdetail.getRealname());//用户姓名
//        zlCleanOrder.setTel(zlWxuserdetail.getTel());//用户手机号
        zlCleanOrder.setHotelid(cleanParm.getHotelid());   //酒店ID
        zlCleanOrder.setHotelname(cleanParm.getHotelname());   //酒店名
        zlCleanOrder.setRoomid(cleanParm.getRoomid());   //房间ID
        zlCleanOrder.setRoomnumber(cleanParm.getRoomnumber());   //房间号
        zlCleanOrder.setComeformid(1);   //来自1小程序C端，2小程序B端，3公众号, 4民宿，5好评返现，6分时酒店
        zlCleanOrder.setBookdate((int) (cleanParm.getBookdate() / 1000));   //预定时间
//        zlCleanOrder.setBookdate(11212);   //预定时间
        zlCleanOrder.setRemark(cleanParm.getRemark());   //其他需求备注
        zlCleanOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));   //下单时间
        zlCleanOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));   //支付/取消时间
//        zlCleanOrderMapper.addCleanOrder(zlCleanOrder);
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(cleanParm.getRoomnumber(), cleanParm.getHotelid());
        zlCleanOrder.setFloornumber(zlHotelroom.getRoomfloor());
        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userid);
        ZlWxuser wxuser = zlWxuserMapper.selectOne(zlWxuser);
        zlCleanOrder.setUsername(wxuser.getNickname());
        zlCleanOrderMapper.insertSelective(zlCleanOrder);

        logger.info("清扫订单插入数据库完成,订单id:" + zlCleanOrder.getOrderid());

        // 推送消息
        CleanOrderToPhpVO cleanOrderVO = zlCleanOrderMapper.selectToPhp(zlCleanOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_CLEAN, cleanOrderVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_CLEAN_ORDER);
        orderPhpSendVO.setMessage(cleanOrderVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_CLEAN_ORDER, orderStr);
        logger.info("推送清扫订单到redis通知php后台人员完成,订单信息:" + cleanOrderVO);

        map.put("serialnumber", serialnumber);
        map.put("orderid", zlCleanOrder.getOrderid());
        return map;
    }

    @Override
    public Map<String, Object> selectCleanDetails(Long orderID) {
        return zlCleanOrderMapper.selectCleanDetails(orderID);
    }

    @Override
    public void removeCleanOrder(Long orderID, Integer updatedate) {
        zlCleanOrderMapper.removeCleanOrder(orderID, updatedate);
    }

    @Override
    public void deleteCleanOrder(Long orderID) {
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        zlCleanOrderMapper.deleteCleanOrder(orderID, nowTime);
    }


}
