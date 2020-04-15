package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ZlOrderDetailIml implements ZlOrderDetailService{
    
    @Autowired
    private ZlOrderDetailMapper orderMapper;
    
    @Override
    public ZlOrderDetail findOrder(Long orderID){
        return orderMapper.findOrder(orderID);
    }
    
}
