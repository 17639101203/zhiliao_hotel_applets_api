package com.zhiliao.hotel.controller.myOrder.vo;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-22 17:30
 **/
public class GoodsShortInfoVO {

    /**
     * 商品ID
     */
    private Integer goodsID;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongModule;

    /**
     * 商品SkuID
     */
    private Integer skuID;

    /**
     * 酒店HotelID
     */
    private Integer hotelID;

    public Short getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(Short belongModule) {
        this.belongModule = belongModule;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public Integer getSkuID() {
        return skuID;
    }

    public void setSkuID(Integer skuID) {
        this.skuID = skuID;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }
}
