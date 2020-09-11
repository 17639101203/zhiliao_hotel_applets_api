package com.zhiliao.hotel.controller.coupon.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-07 11:18
 **/
@Data
public class ZlCouponInfoVO {

    /**
     * zl_coupon表ID
     */
    private Integer couponid;

    /**
     * zl_couponrule表ID，优惠券规则ID
     */
    private Integer couponruleid;

    /**
     * 优惠券类型 默认0，1：满减券 2:折扣券 3:代金券
     */
    private Byte type;

    /**
     * 有效期限类型(0：区间时间 1:自领取日起N天)
     */
    private Byte effecttype;

    /**
     * 有效期：开始时间
     */
    private Integer starttime;

    /**
     * 有效期：结束时间
     */
    private Integer endtime;

    /**
     * 有效期：领取日起N天
     */
    private Integer days;

}
