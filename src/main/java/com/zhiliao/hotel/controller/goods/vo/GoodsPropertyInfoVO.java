package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-04 16:14
 **/
@Data
@ApiModel("用户选择的商品规格的信息")
public class GoodsPropertyInfoVO {

    @ApiModelProperty(value = "酒店商品skuId")
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "库存")
    private Integer stockCount;

    @ApiModelProperty(value = "属性分类ID")
    private String propertyId;

}
