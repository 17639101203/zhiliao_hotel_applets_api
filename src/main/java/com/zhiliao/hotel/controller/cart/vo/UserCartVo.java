package com.zhiliao.hotel.controller.cart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by xiegege on 2020/4/14.
 */

@ApiModel("用户购物车")
@Data
public class UserCartVo {

    @ApiModelProperty(value = "购物车id")
    private Long cartId;

    @ApiModelProperty(value = "酒店商品skuId")
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品封面图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "规格名称")
    private String propertyName;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsCount;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产")
    private Integer belongModule;
}