package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-02 09:46
 **/
@Data
public class GoodsPropertysVO {

    @ApiModelProperty(value = "酒店商品skuId")
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "库存")
    private Integer stockCount;

    @ApiModelProperty(value = "规格分类Id")
    private List<Integer> propertyIds;

}
