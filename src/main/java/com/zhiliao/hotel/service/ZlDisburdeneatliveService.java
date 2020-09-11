package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.eatRent.vo.ZlDisburdeneatliveVO;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;

/**
 *
 */
public interface ZlDisburdeneatliveService {

    ZlDisburdeneatliveVO findEatRentInfo(Integer hotelId, Integer roomId);

}
