package com.zhiliao.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情表
 *
 * @author null
 * @date 2020-04-15
 */
public class ZlOrderDetail implements Serializable {
    /**
     *
     */
    private Long orderDetailID;

    /**
     *
     */
    private Long orderID;

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 商品ID/设施ID (由OrderType决定)
     */
    private Integer goodsID;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsCoverUrl;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 删除状态:0正常;1删除;
     */
    private Integer isDelete;

    /**
     * 下单时间
     */
    private Integer createDate;

    /**
     * 支付/取消时间
     */
    private Integer updateDate;

    public Long getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Long orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

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

    public String getGoodsCoverUrl() {
        return goodsCoverUrl;
    }

    public void setGoodsCoverUrl(String goodsCoverUrl) {
        this.goodsCoverUrl = goodsCoverUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }
}
