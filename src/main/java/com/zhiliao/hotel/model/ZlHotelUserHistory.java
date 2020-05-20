package com.zhiliao.hotel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "zl_hoteluserhistory")
public class ZlHotelUserHistory implements Serializable {

    @ApiModelProperty(value = "自增id")
    private Long recid;

    @ApiModelProperty(value = "用户id")
    private Long userid;

    @ApiModelProperty(value = "酒店id")
    private Integer hotelid;

    @ApiModelProperty(value = "酒店名称")
    private String hotelname;

    @ApiModelProperty(value = "房间id")
    private Integer roomid;

    @ApiModelProperty(value = "客房编号")
    private String roomnumber;

    @ApiModelProperty(value = "入住日期")
    @JsonFormat(timezone = "", pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer checkindate;

    @ApiModelProperty(value = "添加日期")
    @JsonFormat(timezone = "", pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer createdate;

    public Long getRecid() {
        return recid;
    }

    public void setRecid(Long recid) {
        this.recid = recid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

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

    public Integer getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(Integer checkindate) {
        this.checkindate = checkindate;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }
}

