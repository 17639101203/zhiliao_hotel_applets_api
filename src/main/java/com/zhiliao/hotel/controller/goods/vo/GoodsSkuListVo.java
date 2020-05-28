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

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "库存")
    private Integer stockCount;
}