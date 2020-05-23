package com.zhiliao.hotel.controller.myOrder.vo;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-22 16:41
 **/
public class OrderDetailVO {

    /**
     * 酒店商品ID
     */
    private Integer hotelGoodsID;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 单价
     */
    private BigDecimal price;

    public Integer getHotelGoodsID() {
        return hotelGoodsID;
    }

    public void setHotelGoodsID(Integer hotelGoodsID) {
        this.hotelGoodsID = hotelGoodsID;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
