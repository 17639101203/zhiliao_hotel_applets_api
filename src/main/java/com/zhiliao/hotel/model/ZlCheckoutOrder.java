package com.zhiliao.hotel.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author null
 * @date 2020-06-06
 */
@Table(name="zl_checkoutorder")
public class ZlCheckoutOrder implements Serializable {
    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 订单编号
     */
    private String orderserialno;

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
     * 用户名
     */
    private String username;

    /**
     * 手机
     */
    private String tel;

    /**
     * 订单状态:-1:取消退房;0等待退房;1完成退房
     */
    private Byte orderstatus;

    /**
     * 操作员
     */
    private String operatorname;

    /**
     * 操作员IP
     */
    private String operatorip;

    /**
     * 操作员备注
     */
    private String operatorremark;

    /**
     * 退房时间
     */
    private Integer checkoutdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间（下单时间)
     */
    private Integer createdate;

    /**
     * 修改时间（操作时间）
     */
    private Integer updatedate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_checkoutorder
     *
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OrderID
     *
     * @return the value of zl_checkoutorder.OrderID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Long getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OrderID
     *
     * @param orderid the value for zl_checkoutorder.OrderID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.UserID
     *
     * @return the value of zl_checkoutorder.UserID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.UserID
     *
     * @param userid the value for zl_checkoutorder.UserID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OrderSerialNo
     *
     * @return the value of zl_checkoutorder.OrderSerialNo
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getOrderserialno() {
        return orderserialno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OrderSerialNo
     *
     * @param orderserialno the value for zl_checkoutorder.OrderSerialNo
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOrderserialno(String orderserialno) {
        this.orderserialno = orderserialno == null ? null : orderserialno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.HotelID
     *
     * @return the value of zl_checkoutorder.HotelID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Integer getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.HotelID
     *
     * @param hotelid the value for zl_checkoutorder.HotelID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.HotelName
     *
     * @return the value of zl_checkoutorder.HotelName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getHotelname() {
        return hotelname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.HotelName
     *
     * @param hotelname the value for zl_checkoutorder.HotelName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setHotelname(String hotelname) {
        this.hotelname = hotelname == null ? null : hotelname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.RoomID
     *
     * @return the value of zl_checkoutorder.RoomID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Integer getRoomid() {
        return roomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.RoomID
     *
     * @param roomid the value for zl_checkoutorder.RoomID
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.RoomNumber
     *
     * @return the value of zl_checkoutorder.RoomNumber
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getRoomnumber() {
        return roomnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.RoomNumber
     *
     * @param roomnumber the value for zl_checkoutorder.RoomNumber
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber == null ? null : roomnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.UserName
     *
     * @return the value of zl_checkoutorder.UserName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.UserName
     *
     * @param username the value for zl_checkoutorder.UserName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.Tel
     *
     * @return the value of zl_checkoutorder.Tel
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.Tel
     *
     * @param tel the value for zl_checkoutorder.Tel
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OrderStatus
     *
     * @return the value of zl_checkoutorder.OrderStatus
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Byte getOrderstatus() {
        return orderstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OrderStatus
     *
     * @param orderstatus the value for zl_checkoutorder.OrderStatus
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOrderstatus(Byte orderstatus) {
        this.orderstatus = orderstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OperatorName
     *
     * @return the value of zl_checkoutorder.OperatorName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getOperatorname() {
        return operatorname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OperatorName
     *
     * @param operatorname the value for zl_checkoutorder.OperatorName
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname == null ? null : operatorname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OperatorIP
     *
     * @return the value of zl_checkoutorder.OperatorIP
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getOperatorip() {
        return operatorip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OperatorIP
     *
     * @param operatorip the value for zl_checkoutorder.OperatorIP
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOperatorip(String operatorip) {
        this.operatorip = operatorip == null ? null : operatorip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.OperatorRemark
     *
     * @return the value of zl_checkoutorder.OperatorRemark
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getOperatorremark() {
        return operatorremark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.OperatorRemark
     *
     * @param operatorremark the value for zl_checkoutorder.OperatorRemark
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setOperatorremark(String operatorremark) {
        this.operatorremark = operatorremark == null ? null : operatorremark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.CheckOutDate
     *
     * @return the value of zl_checkoutorder.CheckOutDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Integer getCheckoutdate() {
        return checkoutdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.CheckOutDate
     *
     * @param checkoutdate the value for zl_checkoutorder.CheckOutDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setCheckoutdate(Integer checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.Remark
     *
     * @return the value of zl_checkoutorder.Remark
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.Remark
     *
     * @param remark the value for zl_checkoutorder.Remark
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.IsUserDelete
     *
     * @return the value of zl_checkoutorder.IsUserDelete
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Boolean getIsuserdelete() {
        return isuserdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.IsUserDelete
     *
     * @param isuserdelete the value for zl_checkoutorder.IsUserDelete
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setIsuserdelete(Boolean isuserdelete) {
        this.isuserdelete = isuserdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.IsDelete
     *
     * @return the value of zl_checkoutorder.IsDelete
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.IsDelete
     *
     * @param isdelete the value for zl_checkoutorder.IsDelete
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.CreateDate
     *
     * @return the value of zl_checkoutorder.CreateDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.CreateDate
     *
     * @param createdate the value for zl_checkoutorder.CreateDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_checkoutorder.UpdateDate
     *
     * @return the value of zl_checkoutorder.UpdateDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public Integer getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_checkoutorder.UpdateDate
     *
     * @param updatedate the value for zl_checkoutorder.UpdateDate
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_checkoutorder
     *
     * @mbg.generated Sat Jun 06 15:06:18 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderid=").append(orderid);
        sb.append(", userid=").append(userid);
        sb.append(", orderserialno=").append(orderserialno);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", hotelname=").append(hotelname);
        sb.append(", roomid=").append(roomid);
        sb.append(", roomnumber=").append(roomnumber);
        sb.append(", username=").append(username);
        sb.append(", tel=").append(tel);
        sb.append(", orderstatus=").append(orderstatus);
        sb.append(", operatorname=").append(operatorname);
        sb.append(", operatorip=").append(operatorip);
        sb.append(", operatorremark=").append(operatorremark);
        sb.append(", checkoutdate=").append(checkoutdate);
        sb.append(", remark=").append(remark);
        sb.append(", isuserdelete=").append(isuserdelete);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}