package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlHotelFacilityMapper;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
import com.zhiliao.hotel.utils.OrderSerialNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    /**
     * 获取酒店设施列表
     * @param hotelId
     * @return
     */
    @Override
    public List<ZlHotelFacility> getHotelFacilityList(Integer hotelId) {
        return hotelFacilityMapper.getHotelFacilityList(hotelId);
    }

    /**
     * 酒店设施预定详情
     * @param facilityId
     * @return
     */
    @Override
    public ZlHotelFacility getHotelFacilityDetail(Integer facilityId) {
        return hotelFacilityMapper.getHotelFacilityDetail(facilityId);
    }

    /**
     * 酒店设施预定
     * @param zlHotelFacilityOrder
     * @param facilityID
     * @return
     */
    @Override
    public Map<String, Object> addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder, Integer facilityID) {
        ZlHotel zlHotel = hotelMapper.getById(String.valueOf(zlHotelFacilityOrder.getHotelid()));
        zlHotelFacilityOrder.setSerialnumber(OrderSerialNoUtil.CreateOrderSerialNo("HS"));
        zlHotelFacilityOrder.setComeformid(1);
        if (zlHotel != null){
            zlHotelFacilityOrder.setHotelname(zlHotel.getHotelName());
        }
        if (zlHotelFacilityOrder.getPaytype() == 0 && zlHotelFacilityOrder.getActuallypay().compareTo(BigDecimal.valueOf(0)) < 1){
           zlHotelFacilityOrder.setPaystatus((byte) 0);
        }else {
            zlHotelFacilityOrder.setPaystatus((byte) 1);
        }
        zlHotelFacilityOrder.setOrderstatus((byte) 0);
        zlHotelFacilityOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        int insert = facilityOrderMapper.insert(zlHotelFacilityOrder);
        if (insert > 0){
            hotelFacilityMapper.updateCount(facilityID, (int) new Date().getTime());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",zlHotelFacilityOrder.getOrderid());
        return map;
    }
}
