package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;

public interface ZlOrderService{
    
    PageInfoResult findAllOrder(Long userID,Integer orderType,Integer orderStatus,Integer payStatus,Integer payType,Integer pageNo,Integer pageSize);
    
    void byOrderId(Long orderID);
    
}
