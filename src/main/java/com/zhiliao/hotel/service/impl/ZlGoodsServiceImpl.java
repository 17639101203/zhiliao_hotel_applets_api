package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlGoodsMapper;
import com.zhiliao.hotel.service.ZlGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiegege on 2020/4/15.
 */

@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {


    private ZlGoodsMapper zlGoodsMapper;

    @Autowired
    public ZlGoodsServiceImpl(ZlGoodsMapper zlGoodsMapper) {
        this.zlGoodsMapper = zlGoodsMapper;
    }
}