package com.zhiliao.hotel.controller.myOrder.vo;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-28 11:35
 **/
public class OrderPayShortInfoVO {

    /**
     * 订单ID
     */
    private Long orderID;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallyPay;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getActuallyPay() {
        return actuallyPay;
    }

    public void setActuallyPay(BigDecimal actuallyPay) {
        this.actuallyPay = actuallyPay;
    }
}
