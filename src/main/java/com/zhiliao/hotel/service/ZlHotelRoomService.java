package com.zhiliao.hotel.service;

import java.util.Map;

/**
 * 查询wifi
 */
public interface ZlHotelRoomService {
    Map<String, String> findWiFi(Integer hotelid);
}
