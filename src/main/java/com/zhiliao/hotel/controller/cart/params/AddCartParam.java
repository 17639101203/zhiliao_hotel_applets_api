package com.zhiliao.hotel.controller.cart.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiegege
 * @date 2020/4/12
 */

@ApiModel("购物车添加")
@Data
public class AddCartParam {

    @ApiModelProperty(value = "酒店商品skuId", required = true)
    private Integer hotelGoodsSkuId;

    @ApiModelProperty(value = "商品数量", required = true)
    private Integer goodsCount;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产", required = true)
    private Integer belongModule;
}