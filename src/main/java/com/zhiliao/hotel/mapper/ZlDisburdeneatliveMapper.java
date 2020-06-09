package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 */
public interface ZlDisburdeneatliveMapper extends Mapper<ZlDisburdeneatlive> {

    ZlDisburdeneatlive find(@Param("hotelID") Integer hotelID);

}
