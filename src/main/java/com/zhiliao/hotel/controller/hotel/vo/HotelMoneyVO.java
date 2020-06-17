package com.zhiliao.hotel.controller.hotel.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-17 17:47
 **/
@Data
public class HotelMoneyVO {

    /**
     * 配送金额
     */
    private BigDecimal minSendCash;

    /**
     * 起送金额
     */
    private BigDecimal sendCash;

}
