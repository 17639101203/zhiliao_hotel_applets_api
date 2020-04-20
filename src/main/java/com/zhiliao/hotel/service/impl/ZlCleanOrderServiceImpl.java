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

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCleanOrderServiceImpl implements ZlCleanOrderService {

    @Autowired
    private ZlCleanOrderMapper zlCleanOrderMapper;

    @Override
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder) {
        return zlCleanOrderMapper.addCleanOrder(zlCleanOrder);
    }


}
