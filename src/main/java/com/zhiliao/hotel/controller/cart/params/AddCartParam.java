package com.zhiliao.hotel.controller.cart.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiegege
 * @date 2020/4/12
 */

@ApiModel("购物车添加")
public class AddCartParam {

    @ApiModelProperty(value = "商品id", required = true)
    private Integer goodsId;

    @ApiModelProperty(value = "商品名", required = true)
    private String goodsName;

    @ApiModelProperty(value = "数量", required = true)
    private Integer num;

    @ApiModelProperty(value = "商品金额", required = true)
    private Float price;

    @ApiModelProperty(value = "商品图片", required = true)
    private String url;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}