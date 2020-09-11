package com.zhiliao.hotel.controller.myOrder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 */
@ApiModel("查询订单")
@Data
public class OrderInfoDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 酒店ID
     */
    @NotNull(message = "酒店id不能为空")
    @ApiModelProperty(value = "酒店id")
    private Integer hotelId;

    /**
     * 订单状态:0全部;1:待付款;2待收货
     */
    @NotNull(message = "订单状态不能为空")
    @ApiModelProperty(value = "订单状态:0全部;1:待付款;2待收货", allowableValues = "0,1,2")
    private Byte orderstatus;

    /**
     * 所属模块： 1便利店；2餐饮服务；3情趣用品；4土特产。
     */
    @ApiModelProperty(value = "所属模块：1便利店；2餐饮服务；3情趣用品；4土特产。", allowableValues = "1,2,3,4")
    private Short belongmodule;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNo;

    /**
     * 每页显示条数
     */
    @NotNull(message = "每页显示条数不能为空")
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer pageSize;

}
