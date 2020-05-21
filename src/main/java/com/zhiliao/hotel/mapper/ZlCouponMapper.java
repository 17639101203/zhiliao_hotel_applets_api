package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCoupon;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 15:34
 **/
public interface ZlCouponMapper extends Mapper<ZlCoupon> {
    ZlCoupon findByCouponID(Integer couponID);
}
