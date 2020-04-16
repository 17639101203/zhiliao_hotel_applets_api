package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.service.ZlOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderServiceIml implements ZlOrderService{
    
    @Autowired
    private ZlOrderMapper orderMapper;
    @Autowired
    private ZlOrderDetailMapper orderDetailMapper;
    
    @Override
    public List<ZlOrder> findAllOrder(Long userID){
        return orderMapper.findAllOrder(userID);
    }
    
    @Override
    public List<ZlOrder> findOrderByPayStatus(Long userID,Integer payStatus){
        return orderMapper.findOrderByPayStatus(userID,payStatus);
    }

    @Override
    public void byOrderId(Long orderID) {
        ZlOrder order = orderMapper.findById(orderID);
        if (order != null){
            order.setOrderstatus((byte) -1);
            order.setUpdatedate((int) new Date().getTime());
        }

        //修改订单表
        orderMapper.byOrderId(order);
        //修改订单详情表
        orderDetailMapper.byOrderdetailId(order);
    }
    
}
