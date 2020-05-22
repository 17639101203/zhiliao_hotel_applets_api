package com.zhiliao.hotel.controller.serviceorder.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 客房服务订单提交dto
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@ApiModel("客房服务订单提交实体")
public class ServiceorderCommitParams implements Serializable {

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店id", required = true)
    private Integer hotelid;

    /**
     * 酒店名
     */
    @ApiModelProperty(value = "酒店名称", required = true)
    private String hotelname;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间id")
    private Integer roomid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    /**
     * 其它需求备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
