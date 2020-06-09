package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlDisburdeneatliveMapper;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import com.zhiliao.hotel.service.ZlDisburdeneatliveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class ZlDisburdeneatliveServiceImpl implements ZlDisburdeneatliveService{
    
    @Autowired
    private ZlDisburdeneatliveMapper disburdeneatliveMapper;
    
    @Override
    public ZlDisburdeneatlive findEatRentInfo(Integer hotelId){
        return disburdeneatliveMapper.find(hotelId);
    }
    
}
