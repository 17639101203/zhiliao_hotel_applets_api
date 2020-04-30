package com.zhiliao.hotel.controller.myOrder.vo;

import java.math.BigDecimal;

/**
 * 商品信息VO
 */
public class GoodsInfoVO {

    /**
     * 商品ID
     */
    private Integer goodsID;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Integer orderType;

    /**
     * 商品封面图片
     */
    private String coverImgUrl;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer goodsCount;

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
