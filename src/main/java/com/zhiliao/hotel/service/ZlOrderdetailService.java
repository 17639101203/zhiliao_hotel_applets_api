package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlOrderdetail;

import java.util.List;

public interface ZlOrderdetailService{
    
    List<ZlOrderdetail> findOrder(Long orderID);
    
}
