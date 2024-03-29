package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.hotelfacility.vo.ZlHotelFacilityOrderToPhpVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.PushInfoToPhpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityServiceImpl implements ZlHotelFacilityService {

    private static final Logger logger = LoggerFactory.getLogger(ZlHotelFacilityServiceImpl.class);

    @Autowired
    private ZlHotelFacilityMapper hotelFacilityMapper;

    @Autowired
    private ZlHotelMapper hotelMapper;

    @Autowired
    private ZlHotelFacilityOrderMapper facilityOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    /**
     * 获取酒店设施列表
     *
     * @param hotelId
     * @return
     */
    @Override
    public List<ZlHotelFacility> getHotelFacilityList(Integer hotelId) {
        return hotelFacilityMapper.getHotelFacilityList(hotelId);
    }

    /**
     * 酒店设施预定详情
     *
     * @param facilityId
     * @return
     */
    @Override
    public ZlHotelFacility getHotelFacilityDetail(Integer facilityId) {
        return hotelFacilityMapper.getHotelFacilityDetail(facilityId);
    }

    /**
     * 酒店设施预定
     *
     * @param zlHotelFacilityOrder
     * @return
     */
    @Override
    public Map<String, Object> addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder) {
        Integer facilityID = zlHotelFacilityOrder.getFacilityid();
        //定义map集合,用于封装返回信息
        Map<String, Object> map = new HashMap<>();

        ZlHotelFacility hotelFacilityDetail = hotelFacilityMapper.getHotelFacilityDetail(facilityID);
        if (hotelFacilityDetail == null) {
            throw new RuntimeException("该酒店没有此设施,请询问酒店前台");
        }

        //开始时间
        Long beginusedate = Long.valueOf(zlHotelFacilityOrder.getUsebegindate());
        //结束时间
        Long endusedate = Long.valueOf(zlHotelFacilityOrder.getUseenddate());
        //付费项目
        if (hotelFacilityDetail.getPrice().compareTo(new BigDecimal(0)) >= 0) {
            //判断酒店设施数量是否充足
            if (hotelFacilityDetail.getFacilitycount() <= 0) {
                throw new RuntimeException("酒店设施数量不足!");
            }
//            System.out.println(beginusedate * 1000);
//            System.out.println(endusedate * 1000);
//            System.out.println(DateUtils.timeStamp2Date(String.valueOf(zlHotelFacilityOrder.getUsebegindate() * 1000), null));
//            System.out.println(DateUtils.timeStamp2Date(String.valueOf(zlHotelFacilityOrder.getUseenddate() * 1000), null));
            Long servicebegindate = Long.valueOf(hotelFacilityDetail.getServicebegindate());
            Long serviceenddate = Long.valueOf(hotelFacilityDetail.getServiceenddate());
            //判断该时间段是否在营业时间
            String beginusedateStr = DateUtils.timeStamp2Date(String.valueOf(beginusedate * 1000), null);
            String endusedateStr = DateUtils.timeStamp2Date(String.valueOf(endusedate * 1000), null);
            String servicebegindateStr = DateUtils.timeStamp2Date(String.valueOf(servicebegindate * 1000), null);
            String serviceenddateStr = DateUtils.timeStamp2Date(String.valueOf(serviceenddate * 1000), null);
            String[] beginusedateStrArr = beginusedateStr.split(" ");
            String[] endusedateStrArr = endusedateStr.split(" ");
            String[] servicebegindateStrArr = servicebegindateStr.split(" ");
            String[] serviceenddateStrArr = serviceenddateStr.split(" ");
            String serviceBeginDate = beginusedateStrArr[0] + " " + servicebegindateStrArr[1];
            String serviceEndDate = endusedateStrArr[0] + " " + serviceenddateStrArr[1];
            try {
                Long serviceBeginDateNum = Long.valueOf(DateUtils.dateToStamp(serviceBeginDate));
                Long serviceEndDateNum = Long.valueOf(DateUtils.dateToStamp(serviceEndDate));
                if (serviceBeginDateNum > (beginusedate * 1000) || serviceEndDateNum < (endusedate * 1000)) {
                    throw new RuntimeException("该时间段不在此设施的服务时间,请重新选择!");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //生成订单编号
        String serialNumber = OrderIDUtil.createOrderID("SS");
        zlHotelFacilityOrder.setSerialnumber(serialNumber);
        zlHotelFacilityOrder.setComeformid(1);
        ZlHotel zlHotel = hotelMapper.getById(zlHotelFacilityOrder.getHotelid());
        if (zlHotel != null) {
            //获取酒店名称
            zlHotelFacilityOrder.setHotelname(zlHotel.getHotelName());
        }
        zlHotelFacilityOrder.setOrderstatus((byte) 0);
        zlHotelFacilityOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(zlHotelFacilityOrder.getRoomnumber(), zlHotelFacilityOrder.getHotelid());
        if (zlHotelroom == null) {
            throw new BizException("您的码是前台码，不提供该服务");
        }
        zlHotelFacilityOrder.setFloornumber(zlHotelroom.getRoomfloor());
//        ZlWxuser zlWxuser = new ZlWxuser();
//        zlWxuser.setUserid(zlHotelFacilityOrder.getUserid());
//        ZlWxuser wxuser = zlWxuserMapper.selectOne(zlWxuser);
//        zlHotelFacilityOrder.setUsername(wxuser.getNickname());
        if (zlHotelFacilityOrder.getTotalprice().intValue() == 0) {
            zlHotelFacilityOrder.setPaystatus((byte) 2);
        } else {
            zlHotelFacilityOrder.setPaystatus((byte) 1);
        }
        //修改该设施相应信息数量
        hotelFacilityDetail.setFacilitycount(hotelFacilityDetail.getFacilitycount() - 1);
        hotelFacilityDetail.setFacilityremaincount(hotelFacilityDetail.getFacilityremaincount() - 1);
        hotelFacilityDetail.setUsecount(hotelFacilityDetail.getUsecount() + 1);
        hotelFacilityMapper.updateByPrimaryKeySelective(hotelFacilityDetail);
        facilityOrderMapper.insertSelective(zlHotelFacilityOrder);
        logger.info("酒店设施订单插入数据库完成,订单id:" + zlHotelFacilityOrder.getOrderid());

        // 推送消息
        ZlHotelFacilityOrderToPhpVO zlHotelFacilityOrderToPhpVO = facilityOrderMapper.selectToPhp(zlHotelFacilityOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_CKECKOUT, zlHotelFacilityOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_FACILITY_ORDER);
        orderPhpSendVO.setMessage(zlHotelFacilityOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_FACILITY_ORDER, orderStr);
        logger.info("推送酒店设施订单到redis通知php后台人员完成,订单信息:" + zlHotelFacilityOrderToPhpVO);

        map.put("orderid", zlHotelFacilityOrder.getOrderid());
        map.put("serialnumber", serialNumber);
        return map;
    }
}
