package com.zhiliao.hotel.service;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-03 16:06
 **/
public interface OrderLogService {

    /**
     *
     * @param orderid 订单id
     * @param hotelid 酒店id
     * @param createdate 下单时间
     * @param moldtype 订单类型
     */
    void cancelOrderLog(Long orderid, Integer hotelid, Integer createdate, Byte moldtype);

}
