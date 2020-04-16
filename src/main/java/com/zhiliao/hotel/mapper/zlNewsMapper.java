package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.zlNews;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 咨讯mapper层接口
 */
public interface zlNewsMapper extends Mapper<zlNews> {

    List<zlNews> findAllJiuDianId(@Param("hotelID") Integer hotelID, @Param("type") Integer type, @Param("status") Integer status);

    zlNews findById(@Param("newsid") Integer newsid);
}
