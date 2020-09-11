package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.myOrder.dto.OrderDetailInfoDTO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailsReturn;

public interface ZlOrderDetailService{
    
    OrderDetailsReturn findOrder(Long orderId);
    
}
