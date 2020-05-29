package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailsReturn;

public interface ZlOrderDetailService{
    
    OrderDetailsReturn findOrder(OrderDetailInfoVO vo);
    
}
