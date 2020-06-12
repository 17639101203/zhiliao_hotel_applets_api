package com.zhiliao.hotel.controller.servicegoods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiegege
 * @date 2020/4/23
 */

@ApiModel("客房服务商品vo")
@Data
public class ServicegoodsListVo {

    @ApiModelProperty(value = "客房服务商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "客房服务商品名称")
    private String goodsName;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "客房服务商品封面图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "一天可领取次数")
    private Integer dayMaxCount;

    @ApiModelProperty(value = "一天可领取数量")
    private Integer dayMaxGoodsCount;

    @ApiModelProperty(value = "售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "客房服务商品详情")
    private String content;
}