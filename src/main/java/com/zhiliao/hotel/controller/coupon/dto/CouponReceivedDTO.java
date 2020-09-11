package com.zhiliao.hotel.controller.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-10 10:43
 **/
@Data
@ApiModel("优惠券查询DTO")
public class CouponReceivedDTO {

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店id", hidden = false)
    private Integer hotelid;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    @ApiModelProperty(value = "模块id", hidden = false)
    private Integer belongmodule;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    @ApiModelProperty(value = "是否是购物所用, 是: true,否: false", hidden = false)
    private Boolean isforshop;

}
