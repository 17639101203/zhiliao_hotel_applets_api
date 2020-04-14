package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlCartMapper;
import com.zhiliao.hotel.service.ZlCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiegege on 2020/4/14.
 */

@Service
public class ZlCartServiceImpl implements ZlCartService {

    private ZlCartMapper zlCartMapper;

    @Autowired
    public ZlCartServiceImpl(ZlCartMapper zlCartMapper) {
        this.zlCartMapper = zlCartMapper;
    }
}