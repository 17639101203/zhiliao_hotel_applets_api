package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlOrder;

import java.util.List;

public interface ZlOrderService{
    
    List<ZlOrder> findAllOrder(Long userID,Integer pageNum,Integer pageSize);
    
    List<ZlOrder> findOrderByPayStatus(Long userID,Integer payStatus,Integer pageNum,Integer pageSize);

    void byOrderId(Long orderID);
    
}
