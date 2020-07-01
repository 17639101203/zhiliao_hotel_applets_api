package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.mapper.ZlHotelFacilityMapper;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
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

    @Autowired
    private ZlHotelFacilityMapper hotelFacilityMapper;
    @Autowired
    private ZlHotelMapper hotelMapper;

    @Autowired
    private ZlHotelFacilityOrderMapper facilityOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        String serialNumber = OrderIDUtil.createOrderID("");
        zlHotelFacilityOrder.setSerialnumber(OrderIDUtil.createOrderID("HS"));
        zlHotelFacilityOrder.setComeformid(1);
        ZlHotel zlHotel = hotelMapper.getById(zlHotelFacilityOrder.getHotelid());
        if (zlHotel != null) {
            //获取酒店名称
            zlHotelFacilityOrder.setHotelname(zlHotel.getHotelName());
        }
        zlHotelFacilityOrder.setOrderstatus((byte) 0);
        zlHotelFacilityOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        facilityOrderMapper.insertSelective(zlHotelFacilityOrder);

        // 推送消息
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        OrderPhpVO orderPhpVO = new OrderPhpVO();
        orderPhpVO.setOrderID(zlHotelFacilityOrder.getOrderid());
        orderPhpVO.setSerialNumber(serialNumber);
        orderPhpVO.setHotelID(zlHotelFacilityOrder.getHotelid());
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_CKECKOUT);
        orderPhpSendVO.setMessage(orderPhpVO);
        String orderStr = JSON.toJSONString(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_CKECKOUT, orderStr);

        map.put("orderId", zlHotelFacilityOrder.getOrderid());
        return map;
    }
}
