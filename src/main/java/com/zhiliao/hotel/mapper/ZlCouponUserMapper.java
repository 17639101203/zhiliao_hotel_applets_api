package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCouponUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlCouponUserMapper extends Mapper<ZlCouponUser> {
    List<ZlCouponUser> listCouponUser(@Param("userId") Long userId);

    Integer count(@Param("userId") Long userId, @Param("newDate") Integer newDate);
}
