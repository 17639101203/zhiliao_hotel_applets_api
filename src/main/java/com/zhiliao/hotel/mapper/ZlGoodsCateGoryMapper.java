package com.zhiliao.hotel.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ZlGoodsCateGoryMapper {
    List<Map<String, String>> getGoodsCateGory(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule);

}
