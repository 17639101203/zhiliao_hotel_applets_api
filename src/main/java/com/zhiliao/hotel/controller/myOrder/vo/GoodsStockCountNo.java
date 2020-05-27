package com.zhiliao.hotel.controller.myOrder.vo;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-21 11:34
 **/
public class GoodsStockCountNo {

    /**
     * 商品goodsID
     */
    private Integer goodsID;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 酒店商品表自增id
     */
    private Integer hotelGoodsSkuID;

    public Integer getHotelGoodsSkuID() {
        return hotelGoodsSkuID;
    }

    public void setHotelGoodsSkuID(Integer hotelGoodsSkuID) {
        this.hotelGoodsSkuID = hotelGoodsSkuID;
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
}
