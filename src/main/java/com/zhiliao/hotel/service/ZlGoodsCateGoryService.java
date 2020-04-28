package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlGoodsCategory;

import java.util.List;
import java.util.Map;

public interface ZlGoodsCateGoryService {
    List<Map<String,String>> getGoodsCateGory(Integer hotelId, Integer belongModule);
}
