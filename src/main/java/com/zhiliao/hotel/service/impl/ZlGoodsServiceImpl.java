package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlGoodsMapper;
import com.zhiliao.hotel.service.ZlGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {


    private ZlGoodsMapper zlGoodsMapper;

    @Autowired
    public ZlGoodsServiceImpl(ZlGoodsMapper zlGoodsMapper) {
        this.zlGoodsMapper = zlGoodsMapper;
    }

    @Override
    public List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule) {
        return zlGoodsMapper.findGoodsCategory(hotelId, belongModule);
    }
}