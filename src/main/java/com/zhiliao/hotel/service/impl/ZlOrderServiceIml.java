package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.service.ZlOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ZlOrderServiceIml implements ZlOrderService{
    
    @Autowired
    private ZlOrderMapper orderMapper;
    
    @Override
    public List<ZlOrder> findAllOrder(Long userID){
        return orderMapper.findAllOrder(userID);
    }
    
}
