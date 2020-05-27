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

    @ApiModelProperty(value = "酒店商品skuId", required = true)
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "商品id", required = true)
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称", required = true)
    private String goodsName;

    @ApiModelProperty(value = "商品封面图片", required = true)
    private String coverImgUrl;

    @ApiModelProperty(value = "原价", required = true)
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "现价", required = true)
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "销量", required = true)
    private Integer soldCount;

    @ApiModelProperty(value = "虚拟销量", required = true)
    private Integer virtualSoldCount;

    @ApiModelProperty(value = "商品详情", required = true)
    private String content;
}