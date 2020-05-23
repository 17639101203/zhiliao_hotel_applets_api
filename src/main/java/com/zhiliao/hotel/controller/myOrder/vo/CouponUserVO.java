package com.zhiliao.hotel.controller.myOrder.vo;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 14:40
 **/
public class CouponUserVO {

    /**
     * 优惠券ID
     */
    private Integer couponID;

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 满减的金额
     */
    private BigDecimal discountMoney;

    public Integer getCouponID() {
        return couponID;
    }

    public void setCouponID(Integer couponID) {
        this.couponID = couponID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }
}
