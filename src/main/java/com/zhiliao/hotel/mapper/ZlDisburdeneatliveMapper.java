package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.eatRent.vo.ZlDisburdeneatliveVO;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 */
public interface ZlDisburdeneatliveMapper extends Mapper<ZlDisburdeneatlive> {

    ZlDisburdeneatliveVO find(@Param("hotelId") Integer hotelId);

    void addVisitCount(@Param("recid") Integer recid);
}
