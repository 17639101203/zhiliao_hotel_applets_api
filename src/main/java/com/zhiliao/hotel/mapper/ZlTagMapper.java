package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlTag;

import java.util.List;
import java.util.Map;

public interface ZlTagMapper {

    List<Map<String,Object>> getTags(Integer hotelid);
}
