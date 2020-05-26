package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlCleanOrderMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCleanOrderServiceImpl implements ZlCleanOrderService {

    @Autowired
    private  ZlCleanOrderMapper zlCleanOrderMapper;

    @Override
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder) {
        return zlCleanOrderMapper.addCleanOrder(zlCleanOrder);
    }


    @Override
    public Map<String,Object> selectCleanDetails(Long userID, String serialNumber) {
        return zlCleanOrderMapper.selectCleanDetails(userID, serialNumber);
    }

    @Override
    public void removeCleanOrder(Long userID, String serialNumber, Integer updatedate) {
        zlCleanOrderMapper.removeCleanOrder(userID,serialNumber,updatedate);
    }


}
