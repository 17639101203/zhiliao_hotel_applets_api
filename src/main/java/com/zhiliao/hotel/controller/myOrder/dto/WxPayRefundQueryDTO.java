package com.zhiliao.hotel.controller.myOrder.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-17 10:27
 **/
@Data
public class WxPayRefundQueryDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单id不能为空")
    private Long orderid;

    /**
     * 支付订单号
     */
    @NotBlank(message = "支付订单号不能为空")
    @NotNull(message = "支付订单号不能为空")
    private String out_trade_no;

    /**
     * 退款订单号
     */
    @NotBlank(message = "退款订单号不能为空")
    @NotNull(message = "退款订单号不能为空")
    private String out_refund_no;

}
