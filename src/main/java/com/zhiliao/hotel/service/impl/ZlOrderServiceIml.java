package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
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
@Transactional(rollbackFor=Exception.class)
@Service
public class ZlOrderServiceIml implements ZlOrderService{
    
    @Autowired
    private ZlOrderMapper orderMapper;
    @Autowired
    private ZlOrderDetailMapper orderDetailMapper;
    
    @Override
    public PageInfoResult findAllOrder(Long userID,Integer orderType,Integer orderStatus,Integer payStatus,Integer payType,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<ZlOrder> dataList=orderMapper.findAllOrder(userID,orderType,orderStatus,payStatus,payType);
        PageInfo<ZlOrder> pageInfo=new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }
    
    @Override
    public void byOrderId(Long orderID){
        ZlOrder order=orderMapper.findById(orderID);
        if(order!=null){
            order.setOrderstatus((byte)-1);
            order.setUpdatedate((int)new Date().getTime());
        }
        
        //修改订单表
        orderMapper.byOrderId(order);
        //修改订单详情表
        orderDetailMapper.byOrderdetailId(order);
    }
    
}
