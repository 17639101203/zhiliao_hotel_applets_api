package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCouponUser;

import java.util.List;

/**
 * 用户优惠卷
 */
public interface ZlCouponUserService {

    List<ZlCouponUser> listCouponUser(Long userId, Integer pageNo, Integer pageSize);

    Integer count(Long userId);

    PageInfoResult effective(Long userId,Integer pageNo, Integer pageSize);
}
