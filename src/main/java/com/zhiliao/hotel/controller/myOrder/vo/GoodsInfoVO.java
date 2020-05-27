package com.zhiliao.hotel.controller.myOrder.vo;

import java.math.BigDecimal;

/**
 * 商品信息VO
 */
public class GoodsInfoVO {

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongModule;

    /**
     * 商品封面图片
     */
    private String coverImgUrl;

    /**
     * 用户优惠券自增ID
     */
    private Integer recID;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 酒店商品表自增id
     */
    private Integer hotelGoodsSkuID;

    /**
     * 配送地址
     */
    private String DeliveryAddress;

    /**
     * 订单编号
     */
    private String orderSerialNo;

    public Integer getHotelGoodsSkuID() {
        return hotelGoodsSkuID;
    }

    public void setHotelGoodsSkuID(Integer hotelGoodsSkuID) {
        this.hotelGoodsSkuID = hotelGoodsSkuID;
    }

    public String getOrderSerialNo() {
        return orderSerialNo;
    }

    public void setOrderSerialNo(String orderSerialNo) {
        this.orderSerialNo = orderSerialNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Short getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(Short belongModule) {
        this.belongModule = belongModule;
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

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public Integer getRecID() {
        return recID;
    }

    public void setRecID(Integer recID) {
        this.recID = recID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
