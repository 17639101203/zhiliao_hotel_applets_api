package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.coupon.result.ZlCouponUserResult;

import java.util.List;

/**
 * 用户优惠卷
 */
public interface ZlCouponUserService {

    PageInfoResult listCouponUser(Long userId, Integer pageNo, Integer pageSize);

    Integer count(Long userId);

    PageInfoResult effective(Long userId,Integer pageNo, Integer pageSize);
}
