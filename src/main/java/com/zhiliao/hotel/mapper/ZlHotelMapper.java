package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 酒店数据层
 * @author  chenrong
 * @created date 2020/4/10
 */
public interface ZlHotelMapper extends Mapper<ZlHotel> {

    ZlHotel getById(@Param("hotelId") String hotelId);

}
