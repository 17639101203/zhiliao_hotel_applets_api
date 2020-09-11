package com.zhiliao.hotel.controller.myOrder.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-18 10:18
 **/
@Data
public class OrderRefundAppealDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Long orderid;

    /**
     * 订单ID
     */
    @NotNull(message = "申诉内容不能为空")
    @NotBlank(message = "申诉内容不能为空")
    private String  appealcontent;

}
