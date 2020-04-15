package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlOrderDetail;

public interface ZlOrderDetailService{
    
    ZlOrderDetail findOrder(Long orderID);
    
}
