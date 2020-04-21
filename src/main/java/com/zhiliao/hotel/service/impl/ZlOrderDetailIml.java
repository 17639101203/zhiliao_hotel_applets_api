package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderDetailIml implements ZlOrderDetailService{
    

    private final ZlOrderDetailMapper orderMapper;

    @Autowired
    public ZlOrderDetailIml(ZlOrderDetailMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public ZlOrderDetail findOrder(Long userID,Long orderID){
        return orderMapper.findOrder(userID,orderID);
    }
    
}
