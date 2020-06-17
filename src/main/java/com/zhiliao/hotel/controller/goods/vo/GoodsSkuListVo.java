package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiegege
 * @date 2020/5/27 15:15
 */
@ApiModel("酒店商品规格")
@Data
public class GoodsSkuListVo {

    @ApiModelProperty(value = "酒店商品skuId")
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "规格名称")
    private String propertyName;

    @ApiModelProperty(value = "规格图片")
    private String imgUrl;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "库存")
    private Integer stockCount;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产")
    private Byte belongModule;

    @ApiModelProperty(value = "商品图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

}