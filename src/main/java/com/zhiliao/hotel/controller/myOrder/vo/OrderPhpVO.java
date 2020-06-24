package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-24 14:19
 **/
@Data
public class OrderPhpVO implements Serializable {

    /**
     * 订单编号
     */
    private String orderSerialNo;

    /**
     * 酒店id
     */
    private Integer getHotelId;

}
