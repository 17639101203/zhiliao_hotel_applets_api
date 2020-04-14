package com.zhiliao.hotel.controller.cart.vo;

import java.math.BigDecimal;

/**
 * Created by xiegege on 2020/4/14.
 */

public class UserCartVo {
    /**
     * 购物车id
     */
    private Long cartId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 现价
     */
    private BigDecimal currentPrice;
    /**
     * 商品数量
     */
    private Integer goodsCount;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
}