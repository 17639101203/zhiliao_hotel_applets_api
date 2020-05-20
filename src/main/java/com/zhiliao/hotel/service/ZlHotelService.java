package com.zhiliao.hotel.service;


import com.zhiliao.hotel.common.ReturnString;

/**
 * @author  chenrong
 * @created date 2020/4/10
 */
public interface ZlHotelService   {

    ReturnString getById(String hotelId, String roomId, String token);

    /**
     * 获取用户酒店入住历史
     * @param token
     * @return
     */
    ReturnString getHotelHistoryList(String token);
}
