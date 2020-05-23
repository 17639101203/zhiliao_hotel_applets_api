package com.zhiliao.hotel.controller.cart.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiegege
 * @date 2020/4/12
 */

@ApiModel("购物车添加")
public class AddCartParam {

    @ApiModelProperty(value = "商品skuId", required = true)
    private Integer skuId;

    @ApiModelProperty(value = "酒店商品id", required = true)
    private Integer hotelGoodsId;

    @ApiModelProperty(value = "商品数量", required = true)
    private Integer goodsCount;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产", required = true)
    private Integer belongModule;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getHotelGoodsId() {
        return hotelGoodsId;
    }

    public void setHotelGoodsId(Integer hotelGoodsId) {
        this.hotelGoodsId = hotelGoodsId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(Integer belongModule) {
        this.belongModule = belongModule;
    }
}