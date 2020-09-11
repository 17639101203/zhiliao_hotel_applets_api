package com.zhiliao.hotel.controller.coupon.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-10 10:42
 **/
@Data
public class CouponReceivedVO {

    /**
     * 用户优惠券id
     */
    private Long couponuserid;

    /**
     * zl_coupon表ID，所属券的具体规则
     */
    private Integer couponid;

    /**
     * zl_couponrule表ID，优惠券规则ID
     */
    private Integer couponruleid;

    /**
     * 优惠券名称
     */
    private String title;

    /**
     * 优惠券类型 默认0，1：满减券 2:折扣券 3:代金券
     */
    private Byte type;

    /**
     * 优惠券面额；Type:1生效
     */
    private BigDecimal price;

    /**
     * 消费门槛金额；Type:1生效
     */
    private BigDecimal useminprice;

    /**
     * Type:2 时，该字段起作用，代表折扣
     */
    private Float discount;

    /**
     * Type:3时，该字段起作用，代表代金金额
     */
    private BigDecimal pricereplace;

    /**
     * 券状态(-2：已失效 -1：已过期 0：未使用 1：已使用)
     */
    private Byte status;

    /**
     * 使用酒店状态(0：默认全部酒店 1：指定酒店)
     */
    private Byte suithotel;

    /**
     * 使用模块(0：所有模块 1：指定模块<多模块>)
     */
    private Byte suitgoodscategray;

    /**
     * 该优惠券可用模块
     */
    private List<Integer> belongmoduleList;

    /**
     * 有效期：结束时间
     */
    private Integer endtime;

    /**
     * 领取时间
     */
    private Integer createdate;

    /**
     * 规则名称
     */
    private String ruletitle;

}
