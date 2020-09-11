package com.zhiliao.hotel.controller.myOrder.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 酒店基本信息VO
 */
@Data
@ApiModel(value = "酒店基本信息")
public class HotelBasicVO {

    /**
     * 酒店ID
     */
    private Integer hotelID;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 客房ID
     */
    private Integer roomID;

    /**
     * 客房编号
     */
    private String roomNumber;

}
