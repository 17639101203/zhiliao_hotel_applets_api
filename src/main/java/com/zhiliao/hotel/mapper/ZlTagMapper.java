package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlTag;

import java.util.List;

public interface ZlTagMapper {

    public List<ZlTag> getTags(Integer hotelid);
}
