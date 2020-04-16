package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlOrder;

import java.util.List;

public interface ZlOrderService{
    
    List<ZlOrder> findAllOrder(Long userID);
    
    List<ZlOrder> findOrderByPayStatus(Long userID,Integer payStatus);

    void byOrderId(Long orderID);
    
}
