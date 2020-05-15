package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;

public interface ZlHotelFacilityOrderService {
    PageInfoResult findAllOrder(Long userId, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize);

    ZlHotelFacilityOrder findOrder(Long orderID);

    void byOrderId(Long orderID);
}