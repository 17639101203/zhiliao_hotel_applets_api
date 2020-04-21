package com.zhiliao.hotel.service;


import com.zhiliao.hotel.common.ReturnString;

import java.text.ParseException;


/**
 * @author  chenrong
 * @created date 2020/4/10
 */
public interface ZlHotelService   {

    ReturnString getById(String hotelId, String roomId, String token) throws ParseException;
}
