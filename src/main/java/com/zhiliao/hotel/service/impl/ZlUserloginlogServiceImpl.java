package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlUserloginlogMapper;
import com.zhiliao.hotel.model.ZlUserloginlog;
import com.zhiliao.hotel.service.ZlUserloginlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(rollbackFor=Exception.class)
@Service
public class ZlUserloginlogServiceImpl implements ZlUserloginlogService {

    @Resource
    private ZlUserloginlogMapper zlUserloginlogMapper;

    @Override
    public int insert(ZlUserloginlog zlUserloginlog) {
        return zlUserloginlogMapper.insert(zlUserloginlog);
    }
}
