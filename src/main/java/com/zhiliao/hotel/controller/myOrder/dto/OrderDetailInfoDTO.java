package com.zhiliao.hotel.controller.myOrder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
@ApiModel("查询订单详情")
public class OrderDetailInfoDTO {
    
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID", hidden=true)
    private Long userid;
    
    /**
     * 订单ID
     */
    @ApiModelProperty(value="订单ID")
    private Long orderid;

}
