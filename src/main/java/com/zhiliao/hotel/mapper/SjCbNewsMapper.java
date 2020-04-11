package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.SjCbNews;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 咨讯mapper层接口
 */
public interface SjCbNewsMapper extends Mapper<SjCbNews> {

    List<SjCbNews> findAllJiuDianId(@Param("jiudianid") Integer jiudianid);

    SjCbNews findById(@Param("id") Integer id);
}
