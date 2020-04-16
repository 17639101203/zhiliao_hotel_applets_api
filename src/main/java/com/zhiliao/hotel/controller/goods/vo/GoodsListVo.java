package com.zhiliao.hotel.controller.goods.vo;

import java.math.BigDecimal;

/**
 * Created by xiegege on 2020/4/16.
 */
public class GoodsListVo {
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 现价
     */
    private BigDecimal currentPrice;
    /**
     * 总库存
     */
    private Integer totalStockCount;
    /**
     * 总销量
     */
    private Integer totalSoldCount;
    /**
     * 总虚拟销量
     */
    private Integer totalVirtualSoldCount;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getTotalStockCount() {
        return totalStockCount;
    }

    public void setTotalStockCount(Integer totalStockCount) {
        this.totalStockCount = totalStockCount;
    }

    public Integer getTotalSoldCount() {
        return totalSoldCount;
    }

    public void setTotalSoldCount(Integer totalSoldCount) {
        this.totalSoldCount = totalSoldCount;
    }

    public Integer getTotalVirtualSoldCount() {
        return totalVirtualSoldCount;
    }

    public void setTotalVirtualSoldCount(Integer totalVirtualSoldCount) {
        this.totalVirtualSoldCount = totalVirtualSoldCount;
    }
}