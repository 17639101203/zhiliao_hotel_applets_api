package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by xiegege on 2020/4/16.
 */

@ApiModel("酒店商品")
@Data
public class GoodsListVo {

    @ApiModelProperty(value = "酒店商品skuId")
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品封面图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "销量")
    private Integer soldCount;

    @ApiModelProperty(value = "虚拟销量")
    private Integer virtualSoldCount;

    @ApiModelProperty(value = "商品详情")
    private String content;

    @ApiModelProperty(value = "是否多规格")
    private Boolean isManySku;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产")
    private Byte belongModule;

    @ApiModelProperty(value = "库存")
    private Integer stockCount;

    @ApiModelProperty(value = "酒店id")
    private Integer hotelId;

    @ApiModelProperty(value = "供应商id")
    private Integer supplierId;

    @ApiModelProperty(value = "-1:紧急下架;0:安全下架;1上架;2导入待编辑")
    private Integer goodsStatus;

}