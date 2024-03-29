package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.wifi.vo.WifiVo;
import com.zhiliao.hotel.model.ZlHotelroom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 邓菡晨
 * @date 2020/4/14
 */
public interface ZlHotelRoomMapper extends Mapper<ZlHotelroom> {

    List<WifiVo> findWiFi(@Param("hotelID") Integer hotelID, @Param("roomID") Integer roomID);

    ZlHotelroom getById(@Param("roomId") String roomId, @Param("hotelId") Integer hotelId);
    ZlHotelroom getRoomById(@Param("roomId") String roomId);

    ZlHotelroom getByHotelIDAndRoomNumber(@Param("roomNumber") String roomNumber, @Param("hotelId") Integer hotelId);

    boolean updateById(@Param("roomId") String roomId);

    ZlHotelroom getByRoomNumber(@Param("roomNumber") String roomNumber, @Param("hotelID") Integer hotelID);
}
