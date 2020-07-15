package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;

public interface ZlHotelFacilityOrderService {
    PageInfoResult findAllOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize, Integer hotelId);

    HotelFacilityOrderParamVO findOrder(Long orderID);

    void cancelFacilityOrder(Long orderID);

    void userDeleteFacilityOrder(Long orderID);
}
