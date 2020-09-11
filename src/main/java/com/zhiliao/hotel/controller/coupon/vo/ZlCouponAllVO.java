package com.zhiliao.hotel.controller.coupon.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-06 10:16
 **/
@Data
public class ZlCouponAllVO {

    /**
     * 优惠券id
     */
    private Integer id;

    /**
     * 规则id
     */
    private Integer couponruleid;

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

    /**
     * 投放方式(0：默认 1：主动领取 2：定向发放)
     */
    private Byte puttype;

    /**
     * 投放数量(0:不限量 N：N张)
     */
    private Integer putnum;

    /**
     * 领取时间：开始时间
     */
    private Integer gainstarttime;

    /**
     * 领取时间：结束时间
     */
    private Integer gainendtime;

    /**
     * 领取次数限制
     */
    private Byte gainnum;

    /**
     * 适用领取人(0：默认全部注册用户 1:指定用户群体 2:指定个别用户)
     */
    private Byte suitusergroup;

}
