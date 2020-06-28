package com.zhiliao.hotel.service;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-28 15:41
 **/
public interface DeleteOrderService {
    void deleteOrder(Long orderid, Integer orderServiceType);
}
