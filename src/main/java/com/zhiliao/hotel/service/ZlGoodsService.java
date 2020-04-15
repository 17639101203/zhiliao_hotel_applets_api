package com.zhiliao.hotel.service;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsService {

    List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule);
}
