package com.zhiliao.hotel.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-07 11:13
 **/
public interface ZlCouponuserMapper extends Mapper<ZlCouponuser> {
    void updateCouponUserStatus(@Param("couponUserId") String couponUserId);

    void updateStatus(@Param("couponuserid") Long couponuserid);
}
