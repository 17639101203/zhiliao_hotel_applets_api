package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 客房信息
 *
 * @author denghanchen
 * @date 2020-04-14
 */
public class ZlHotelroom implements Serializable {
    /**
     * 
     */
    private Integer roomid;

    /**
     * 房型ID
     */
    private Integer roomtypeid;

    /**
     * 所属酒店ID
     */
    private Integer hotelid;

    /**
     * 客房楼层
     */
    private String roomfloor;

    /**
     * 客房编号
     */
    private String roomnumber;

    /**
     * 房型
     */
    private String model;

    /**
     * wifi名称
     */
    private String wifiname;

    /**
     * wifi密码
     */
    private String wifipwd;

    /**
     * 是否已绑定，0未绑定，1已绑定
     */
    private Byte roomstatus;

    /**
     * 二维码编号
     */
    private String serialnumber;

    /**
     * 是否是房间 0否;1是
     */
    private Boolean isroom;

    /**
     * 
     */
    private String isdelete;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 创建时间
     */
    private Integer createdate;


    private static final long serialVersionUID = 1L;


    public Integer getRoomid() {
        return roomid;
    }


    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }


    public Integer getRoomtypeid() {
        return roomtypeid;
    }


    public void setRoomtypeid(Integer roomtypeid) {
        this.roomtypeid = roomtypeid;
    }


    public Integer getHotelid() {
        return hotelid;
    }


    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }


    public String getRoomfloor() {
        return roomfloor;
    }


    public void setRoomfloor(String roomfloor) {
        this.roomfloor = roomfloor == null ? null : roomfloor.trim();
    }


    public String getRoomnumber() {
        return roomnumber;
    }


    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber == null ? null : roomnumber.trim();
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }




    public String getWifiname() {
        return wifiname;
    }


    public void setWifiname(String wifiname) {
        this.wifiname = wifiname == null ? null : wifiname.trim();
    }


    public String getWifipwd() {
        return wifipwd;
    }


    public void setWifipwd(String wifipwd) {
        this.wifipwd = wifipwd == null ? null : wifipwd.trim();
    }


    public Byte getRoomstatus() {
        return roomstatus;
    }


    public void setRoomstatus(Byte roomstatus) {
        this.roomstatus = roomstatus;
    }


    public String getSerialnumber() {
        return serialnumber;
    }


    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber == null ? null : serialnumber.trim();
    }


    public Boolean getIsroom() {
        return isroom;
    }


    public void setIsroom(Boolean isroom) {
        this.isroom = isroom;
    }


    public String getIsdelete() {
        return isdelete;
    }


    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }


    public Integer getUpdatedate() {
        return updatedate;
    }


    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }


    public Integer getCreatedate() {
        return createdate;
    }


    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roomid=").append(roomid);
        sb.append(", roomtypeid=").append(roomtypeid);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", roomfloor=").append(roomfloor);
        sb.append(", roomnumber=").append(roomnumber);
        sb.append(", model=").append(model);
        sb.append(", wifiname=").append(wifiname);
        sb.append(", wifipwd=").append(wifipwd);
        sb.append(", roomstatus=").append(roomstatus);
        sb.append(", serialnumber=").append(serialnumber);
        sb.append(", isroom=").append(isroom);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", createdate=").append(createdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}