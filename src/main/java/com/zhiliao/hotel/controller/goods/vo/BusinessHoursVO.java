package com.zhiliao.hotel.controller.goods.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-16 09:50
 **/
@Data
public class BusinessHoursVO {

    /**
     * 开始营业时间
     */
    private Long startBusinessHours;

    /**
     * 结束营业时间
     */
    private Long endBusinessHours;

}
