package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlNews;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 咨讯mapper层接口
 */
public interface ZlNewsMapper extends Mapper<ZlNews> {

    List<ZlNews> findAllJiuDianId(@Param("hotelID") Integer hotelID, @Param("type") Integer type, @Param("status") Integer status);

    ZlNews findById(@Param("newsid") Integer newsid);
}
