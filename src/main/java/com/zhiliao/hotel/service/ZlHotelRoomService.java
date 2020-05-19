package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.wifi.vo.WifiVo;

import java.util.List;
import java.util.Map;

/**
 * 查询wifi
 */
public interface ZlHotelRoomService {

    List<WifiVo> findWiFi(Integer hotelID);
}
