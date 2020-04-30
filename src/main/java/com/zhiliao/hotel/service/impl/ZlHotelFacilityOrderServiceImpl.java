package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityOrderServiceImpl implements ZlHotelFacilityOrderService {

    @Autowired
    private ZlHotelFacilityOrderMapper hotelFacilityOrderMapper;

    /**
     * 酒店设施订单列表
     * @param userId
     * @param orderStatus
     * @param payStatus
     * @param payType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllOrder(Long userId, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlHotelFacilityOrder> hotelFacilityOrderList = hotelFacilityOrderMapper.findAllOrder(userId,orderStatus,payStatus,payType);
        PageInfo<ZlHotelFacilityOrder> pageInfo = new PageInfo<>(hotelFacilityOrderList);
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
    public void byOrderId(Long orderID) {
        ZlHotelFacilityOrder order = new ZlHotelFacilityOrder();
        order.setUserid(orderID);
        order.setOrderstatus((byte) -1);
        order.setUpdatedate((int) new Date().getTime());

        ZlHotelFacilityOrder orderById = hotelFacilityOrderMapper.findOrderById(orderID);
        if (orderById != null){
            hotelFacilityOrderMapper.byOrderId(order);
        }

    }
}
