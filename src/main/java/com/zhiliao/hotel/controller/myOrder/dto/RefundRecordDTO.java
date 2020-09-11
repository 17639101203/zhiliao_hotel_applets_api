package com.zhiliao.hotel.controller.myOrder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-14 20:44
 **/
@Data
@ApiModel("申请退款DTO")
public class RefundRecordDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID")
    private Long orderid;

    /**
     * 用户申请退款备注
     */
    @ApiModelProperty(value = "用户申请退款备注")
    private String userremark;

    /**
     * 退货商品图片
     */
    @ApiModelProperty(value = "退货商品图片")
    private String refundgoodsurls;

    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空")
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundmoney;

    /**
     * 售后类型：1退货退款,2仅退款
     */
    @NotNull(message = "售后类型不能为空")
    @ApiModelProperty(value = "售后类型: 1退货退款, 2仅退款", allowableValues = "1,2")
    private Byte refundtype;

    /**
     * 订单商品状态 1未收到货 2已收到货
     */
    @NotNull(message = "订单商品状态不能为空")
    @ApiModelProperty(value = "订单商品状态 1未收到货 2已收到货", allowableValues = "1,2")
    private Byte goodsstatus;

}
