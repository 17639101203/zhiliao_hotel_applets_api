package com.zhiliao.hotel.controller.myOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
@ApiModel("查询订单详情")
public class OrderDetailInfoVO{
    
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID", hidden=true)
    private Long userid;
    
    /**
     * 订单详情ID
     */
    @ApiModelProperty(value="订单详情ID")
    private Long orderdetailid;
    
    /**
     * 订单ID
     */
    @ApiModelProperty(value="订单ID")
    private Long orderid;
    
    /**
     * 酒店商品ID
     */
    @ApiModelProperty(value="酒店商品ID")
    private Integer hotelGoodsID;
    
    /**
     * 订单编号
     */
    @ApiModelProperty(value="订单编号")
    private String orderserialno;
    
    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    @ApiModelProperty(value="所属模块：1便利店；2餐饮服务；3情趣用品；4土特产。", allowableValues="1,2,3,4")
    private Short belongmodule;
    
}
