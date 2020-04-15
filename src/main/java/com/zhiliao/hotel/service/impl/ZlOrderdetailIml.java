package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlOrderdetailMapper;
import com.zhiliao.hotel.model.ZlOrderdetail;
import com.zhiliao.hotel.service.ZlOrderdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ZlOrderdetailIml implements ZlOrderdetailService{
    
    @Autowired
    private ZlOrderdetailMapper orderMapper;
    
    @Override
    public List<ZlOrderdetail> findOrder(Long orderID){
        return orderMapper.findOrder(orderID);
    }
    
}
