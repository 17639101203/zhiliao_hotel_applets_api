package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityOrderServiceImpl implements ZlHotelFacilityOrderService {

    @Autowired
    private ZlHotelFacilityOrderMapper hotelFacilityOrderMapper;

    /**
     * 酒店设施订单列表
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllOrder(Long userId, Integer orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> hotelFacilityOrderList = hotelFacilityOrderMapper.findAllOrder(userId,orderStatus);
        List<Map<String,Object>> orders = new ArrayList<>();
        for (int i = 0; i < hotelFacilityOrderList.size(); i++) {
            Map<String, Object> map = hotelFacilityOrderList.get(i);
            map.put("orderServiceType",4);
            orders.add(map);
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(orders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 酒店设施订单详情
     * @param orderID
     * @return
     */
    @Override
    public ZlHotelFacilityOrder findOrder(Long orderID) {
        return hotelFacilityOrderMapper.findOrderById(orderID);
    }

    /**
     * 取消酒店设施订单
     * @param orderID
     */
    @Override
    public ReturnString byOrderId(Long orderID) {

        return null;
    }
}
