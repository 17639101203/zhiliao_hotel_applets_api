package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZlTagMapper {

    List<Map<String,Object>> getTags(@Param("hotelid")Integer hotelid);

    List<String> getTagName(@Param("tagids") String[] tagids);
}
