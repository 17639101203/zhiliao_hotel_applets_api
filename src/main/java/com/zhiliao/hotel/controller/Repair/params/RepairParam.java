package com.zhiliao.hotel.controller.Repair.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("报修对象Repair")
public class RepairParam {

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelid;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间ID", required = true)
    private Integer roomid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;


    /**
     * 报修信息
     */
    @ApiModelProperty(value = "报修信息", required = true)
    private String remark;


    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
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

    @Override
    public String toString() {
        return "RepairParam{" +
                "hotelid=" + hotelid +
                ", roomid=" + roomid +
                ", roomnumber='" + roomnumber + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
