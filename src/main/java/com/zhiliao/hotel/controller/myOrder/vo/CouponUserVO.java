package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 14:40
 **/
@Data
public class CouponUserVO {

    /**
     *  优惠券id
     */
    private Integer couponid;

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

}
