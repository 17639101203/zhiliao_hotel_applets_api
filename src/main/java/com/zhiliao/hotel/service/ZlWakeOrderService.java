package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlWakeOrder;

import java.util.Map;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-05 15:42
 **/
public interface ZlWakeOrderService {
    Map<String,Object> addWakeOrder(Long userId, ZlWakeOrder wakeOrder);

    ZlWakeOrder wakeOrderDetail(Long orderID);

    void cancelWakeOrder(Long orderID);

    void dlWakeOrder(Long orderID);
}
