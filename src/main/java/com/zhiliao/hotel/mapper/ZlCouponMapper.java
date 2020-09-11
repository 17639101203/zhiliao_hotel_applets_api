package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.coupon.vo.CouponReceivedVO;
import com.zhiliao.hotel.controller.coupon.vo.ZlCouponAllVO;
import com.zhiliao.hotel.controller.coupon.vo.ZlCouponInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.CouponUserVO;
import com.zhiliao.hotel.model.ZlCoupon;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 15:34
 **/
public interface ZlCouponMapper extends Mapper<ZlCoupon> {

    List<ZlCouponAllVO> couponUnclaimed();

    Integer getReceiveCouponCount(@Param("couponruleid") Integer couponruleid);

    Integer getReceiveCouponCount2(@Param("couponruleid") Integer couponruleid, @Param("userId") Long userId);

    List<Long> getUserGroup(@Param("couponruleid") Integer couponruleid);

    List<Long> getUserList(@Param("couponruleid") Integer couponruleid);

    ZlCouponInfoVO getZlCouponInfo(@Param("couponId") Integer couponId, @Param("couponruleId") Integer couponruleId);

    CouponUserVO selectCouponInfo(@Param("couponUserId") Long couponUserId);

    List<CouponReceivedVO> getCouponReceivedAll(@Param("userId") Long userId);

    Integer getCouponCount(@Param("userId") Long userId);

    void updateCouponUser(@Param("couponUserId") Long couponUserId, @Param("useDate") Integer useDate);
}
