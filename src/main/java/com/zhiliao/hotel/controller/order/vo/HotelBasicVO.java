package com.zhiliao.hotel.controller.order.vo;

import io.swagger.annotations.ApiModel;

/**
 * 酒店基本信息VO
 */
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

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
