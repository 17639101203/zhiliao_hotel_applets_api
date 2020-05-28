package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlCoupon;

import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-27 14:08
 **/
public interface ZlCouponService {

    List<ZlCoupon> couponFindAll(Long userId);
}
