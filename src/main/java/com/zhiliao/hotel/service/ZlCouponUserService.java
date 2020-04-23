package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;

/**
 * 用户优惠卷
 */
public interface ZlCouponUserService {

    PageInfoResult listCouponUser(Long userId, Integer pageNo, Integer pageSize);
}
