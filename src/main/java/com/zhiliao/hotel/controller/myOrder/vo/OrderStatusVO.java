package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-26 11:24
 **/
@Data
public class OrderStatusVO {

    /**
     * 订单编号
     */
    private String orderSerialNo;

    /**
     * 支付状态
     */
    private Byte payStatus;

    /**
     * 订单状态
     */
    private Byte orderStatus;

}
