package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelroom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * 酒店客房数据层
 * @author  chenrong
 * @created date 2020/4/14
 */
public interface ZlHotelRoomMapper extends Mapper<ZlHotelroom> {

    @Select("select  WiFiName,WiFiPwd from zl_hotelroom where HotelID = #{hotelid}")
    Map<String, String> findWiFi(@Param("hotelid") Integer hotelid);
}
