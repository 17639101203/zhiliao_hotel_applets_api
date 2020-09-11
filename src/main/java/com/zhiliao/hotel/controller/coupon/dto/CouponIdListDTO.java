package com.zhiliao.hotel.controller.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-07 10:16
 **/
@Data
@ApiModel("用户点击领取优惠券DTO")
public class CouponIdListDTO {

    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id", hidden = false)
    @NotNull(message = "优惠券id不能为空")
    private Integer couponId;

    /**
     * zl_couponrule表ID，优惠券规则ID
     */
    @ApiModelProperty(value = "优惠券规则id", hidden = false)
    @NotNull(message = "优惠券规则id不能为空")
    private Integer couponruleId;

}
