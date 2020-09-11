package com.zhiliao.hotel.controller.myOrder.params;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-09 15:10
 **/
@Data
public class WxPayRefundParam {


    /**
     * 订单ID
     */
    @NotNull(message = "订单id不能为空")
    private Long orderid;

    //退款金额
    @NotNull(message = "退款金额不能为空")
    private BigDecimal refund_fee;

}
