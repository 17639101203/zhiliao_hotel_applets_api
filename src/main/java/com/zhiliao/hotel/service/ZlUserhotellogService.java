package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.wxuser.dto.UserVisitHotelLogDTO;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-22 17:55
 **/
public interface ZlUserhotellogService {

    void userVisitHotelLog(Long userid, UserVisitHotelLogDTO userVisitHotelLogDTO);

}
