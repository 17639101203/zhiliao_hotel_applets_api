package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlGoodsCateGoryMapper;
import com.zhiliao.hotel.service.ZlGoodsCateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlGoodsCateGoryServiceImpl implements ZlGoodsCateGoryService {

    @Autowired
    private ZlGoodsCateGoryMapper goodsCateGoryMapper;
    @Override
    public List<Map<String, String>> getGoodsCateGory(Integer hotelId, Integer belongModule) {
        return goodsCateGoryMapper.getGoodsCateGory(hotelId,belongModule);
    }
}
