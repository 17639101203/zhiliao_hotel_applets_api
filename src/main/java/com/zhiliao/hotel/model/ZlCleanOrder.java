package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-04-14
 */
public class ZlCleanOrder implements Serializable {
    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 订单编号
     */
    private String serialnumber;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 房间ID
     */
    private Integer roomid;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 预定清扫时间
     */
    private Integer bookdate;

    /**
     * 其它需求备注
     */
    private String remark;

    /**
     * -1:已取消;0等待确认;1已确认;2已处理
     */
    private Byte orderstatus;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 支付/取消时间
     */
    private Integer updatedate;
    /**
     * 服务类型
     */
    private String fuwutype;

    public String getFuwutype() {
        return fuwutype;
    }

    public void setFuwutype(String fuwutype) {
        this.fuwutype = fuwutype;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
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

    public Integer getComeformid() {
        return comeformid;
    }

    public void setComeformid(Integer comeformid) {
        this.comeformid = comeformid;
    }

    public Integer getBookdate() {
        return bookdate;
    }

    public void setBookdate(Integer bookdate) {
        this.bookdate = bookdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Byte orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public Integer getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "ZlCleanOrder{" +
                "orderid=" + orderid +
                ", userid=" + userid +
                ", serialnumber='" + serialnumber + '\'' +
                ", hotelid=" + hotelid +
                ", hotelname='" + hotelname + '\'' +
                ", roomid=" + roomid +
                ", roomnumber='" + roomnumber + '\'' +
                ", comeformid=" + comeformid +
                ", bookdate=" + bookdate +
                ", remark='" + remark + '\'' +
                ", orderstatus=" + orderstatus +
                ", isdelete=" + isdelete +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", fuwutype=" + fuwutype +
                '}';
    }

    public ZlCleanOrder(Long orderid, Long userid, String serialnumber, Integer hotelid, String hotelname, Integer roomid, String roomnumber, Integer comeformid, Integer bookdate, String remark, Byte orderstatus, Boolean isdelete, Integer createdate, Integer updatedate) {
        this.orderid = orderid;
        this.userid = userid;
        this.serialnumber = serialnumber;
        this.hotelid = hotelid;
        this.hotelname = hotelname;
        this.roomid = roomid;
        this.roomnumber = roomnumber;
        this.comeformid = comeformid;
        this.bookdate = bookdate;
        this.remark = remark;
        this.orderstatus = orderstatus;
        this.isdelete = isdelete;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.fuwutype = fuwutype;
    }

    public ZlCleanOrder() {
    }
}