package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCouponUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlCouponUserMapper extends Mapper<ZlCouponUser> {
    List<ZlCouponUser> usedCouponUser(@Param("userId") Long userId);

    Integer count(@Param("userId") Long userId, @Param("newDate") Integer newDate);

    List<ZlCouponUser> effectiveCouponUser(@Param("userId") Long userId,@Param("date") Integer date);

    List<ZlCouponUser> beOverdue(@Param("userId") Long userId,@Param("date") Integer date);

    List<ZlCouponUser> list(Long userId);
}
