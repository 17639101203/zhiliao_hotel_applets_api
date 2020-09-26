package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.model.ZlHotel;

/**
 * @author chenrong
 * @created date 2020/4/10
 */
public interface ZlHotelService {

    ReturnString getById(Integer hotelId, String roomId);

    /**
     * 获取用户酒店入住历史
     *
     * @param token
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult getHotelHistoryList(String token, Integer pageNo, Integer pageSize);

    String getReceptionTel(Integer hotelID);

    ZlHotel getByHotelID(Integer hotelId);

    void userDeleteHotelHistory(Long recId);
}
