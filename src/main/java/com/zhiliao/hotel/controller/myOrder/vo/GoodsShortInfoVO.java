package com.zhiliao.hotel.controller.myOrder.vo;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-22 17:30
 **/
public class GoodsShortInfoVO {

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongModule;

    /**
     * 酒店商品表自增id
     */
    private Integer hotelGoodsSkuID;

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

    public Integer getHotelGoodsSkuID() {
        return hotelGoodsSkuID;
    }

    public void setHotelGoodsSkuID(Integer hotelGoodsSkuID) {
        this.hotelGoodsSkuID = hotelGoodsSkuID;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
}
