package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.wifi.vo.WifiVo;
import com.zhiliao.hotel.model.ZlHotelroom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询wifi
 */
public interface ZlHotelRoomService {

    List<WifiVo> findWiFi(@Param("hotelID") Integer hotelID, Integer roomID);

    ZlHotelroom getByRoomNumber(@Param("roomNumber") String roomNumber, @Param("hotelID") Integer hotelID);
}
