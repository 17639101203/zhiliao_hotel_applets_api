package com.zhiliao.hotel.model;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author null
 * @date 2020-04-28
 */
@Table(name = "zl_hotelfacilityorder")
public class ZlHotelFacilityOrder implements Serializable {
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
     * 设施名称
     */
    private String facilityname;

    /**
     * 设施封面图片地址
     */
    private String coverurl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 总价
     */
    private BigDecimal totalprice;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallypay;

    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它
     */
    private Byte paytype;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 预定时间
     */
    private String bookdate;

    /**
     * 0无须支付;1:待支付;2已支付
     */
    private Byte paystatus;

    /**
     * -1:已取消;0等待确认;1已确认
     */
    private Byte orderstatus;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 后台操作人
     */
    private String operatorname;

    /**
     * 操作人IP
     */
    private String operatorip;

    /**
     * 操作人备注
     */
    private String operatorremark;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 支付/取消时间
     */
    private Integer updatedate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_hotelfacilityorder
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.OrderID
     *
     * @return the value of zl_hotelfacilityorder.OrderID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Long getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.OrderID
     *
     * @param orderid the value for zl_hotelfacilityorder.OrderID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.UserID
     *
     * @return the value of zl_hotelfacilityorder.UserID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.UserID
     *
     * @param userid the value for zl_hotelfacilityorder.UserID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.SerialNumber
     *
     * @return the value of zl_hotelfacilityorder.SerialNumber
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getSerialnumber() {
        return serialnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.SerialNumber
     *
     * @param serialnumber the value for zl_hotelfacilityorder.SerialNumber
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber == null ? null : serialnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.HotelID
     *
     * @return the value of zl_hotelfacilityorder.HotelID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Integer getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.HotelID
     *
     * @param hotelid the value for zl_hotelfacilityorder.HotelID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.HotelName
     *
     * @return the value of zl_hotelfacilityorder.HotelName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getHotelname() {
        return hotelname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.HotelName
     *
     * @param hotelname the value for zl_hotelfacilityorder.HotelName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setHotelname(String hotelname) {
        this.hotelname = hotelname == null ? null : hotelname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.RoomID
     *
     * @return the value of zl_hotelfacilityorder.RoomID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Integer getRoomid() {
        return roomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.RoomID
     *
     * @param roomid the value for zl_hotelfacilityorder.RoomID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.RoomNumber
     *
     * @return the value of zl_hotelfacilityorder.RoomNumber
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getRoomnumber() {
        return roomnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.RoomNumber
     *
     * @param roomnumber the value for zl_hotelfacilityorder.RoomNumber
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber == null ? null : roomnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.FacilityName
     *
     * @return the value of zl_hotelfacilityorder.FacilityName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getFacilityname() {
        return facilityname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.FacilityName
     *
     * @param facilityname the value for zl_hotelfacilityorder.FacilityName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname == null ? null : facilityname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.CoverUrl
     *
     * @return the value of zl_hotelfacilityorder.CoverUrl
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getCoverurl() {
        return coverurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.CoverUrl
     *
     * @param coverurl the value for zl_hotelfacilityorder.CoverUrl
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl == null ? null : coverurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.Remark
     *
     * @return the value of zl_hotelfacilityorder.Remark
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.Remark
     *
     * @param remark the value for zl_hotelfacilityorder.Remark
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.TotalPrice
     *
     * @return the value of zl_hotelfacilityorder.TotalPrice
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public BigDecimal getTotalprice() {
        return totalprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.TotalPrice
     *
     * @param totalprice the value for zl_hotelfacilityorder.TotalPrice
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.ActuallyPay
     *
     * @return the value of zl_hotelfacilityorder.ActuallyPay
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public BigDecimal getActuallypay() {
        return actuallypay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.ActuallyPay
     *
     * @param actuallypay the value for zl_hotelfacilityorder.ActuallyPay
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setActuallypay(BigDecimal actuallypay) {
        this.actuallypay = actuallypay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.PayType
     *
     * @return the value of zl_hotelfacilityorder.PayType
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Byte getPaytype() {
        return paytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.PayType
     *
     * @param paytype the value for zl_hotelfacilityorder.PayType
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setPaytype(Byte paytype) {
        this.paytype = paytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.ComeFormID
     *
     * @return the value of zl_hotelfacilityorder.ComeFormID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Integer getComeformid() {
        return comeformid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.ComeFormID
     *
     * @param comeformid the value for zl_hotelfacilityorder.ComeFormID
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setComeformid(Integer comeformid) {
        this.comeformid = comeformid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.BookDate
     *
     * @return the value of zl_hotelfacilityorder.BookDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getBookdate() {
        return bookdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.BookDate
     *
     * @param bookdate the value for zl_hotelfacilityorder.BookDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.PayStatus
     *
     * @return the value of zl_hotelfacilityorder.PayStatus
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Byte getPaystatus() {
        return paystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.PayStatus
     *
     * @param paystatus the value for zl_hotelfacilityorder.PayStatus
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setPaystatus(Byte paystatus) {
        this.paystatus = paystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.OrderStatus
     *
     * @return the value of zl_hotelfacilityorder.OrderStatus
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Byte getOrderstatus() {
        return orderstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.OrderStatus
     *
     * @param orderstatus the value for zl_hotelfacilityorder.OrderStatus
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setOrderstatus(Byte orderstatus) {
        this.orderstatus = orderstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.IsDelete
     *
     * @return the value of zl_hotelfacilityorder.IsDelete
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.IsDelete
     *
     * @param isdelete the value for zl_hotelfacilityorder.IsDelete
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.OperatorName
     *
     * @return the value of zl_hotelfacilityorder.OperatorName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getOperatorname() {
        return operatorname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.OperatorName
     *
     * @param operatorname the value for zl_hotelfacilityorder.OperatorName
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname == null ? null : operatorname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.OperatorIP
     *
     * @return the value of zl_hotelfacilityorder.OperatorIP
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getOperatorip() {
        return operatorip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.OperatorIP
     *
     * @param operatorip the value for zl_hotelfacilityorder.OperatorIP
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setOperatorip(String operatorip) {
        this.operatorip = operatorip == null ? null : operatorip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.OperatorRemark
     *
     * @return the value of zl_hotelfacilityorder.OperatorRemark
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public String getOperatorremark() {
        return operatorremark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.OperatorRemark
     *
     * @param operatorremark the value for zl_hotelfacilityorder.OperatorRemark
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setOperatorremark(String operatorremark) {
        this.operatorremark = operatorremark == null ? null : operatorremark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.CreateDate
     *
     * @return the value of zl_hotelfacilityorder.CreateDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.CreateDate
     *
     * @param createdate the value for zl_hotelfacilityorder.CreateDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacilityorder.UpdateDate
     *
     * @return the value of zl_hotelfacilityorder.UpdateDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public Integer getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacilityorder.UpdateDate
     *
     * @param updatedate the value for zl_hotelfacilityorder.UpdateDate
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_hotelfacilityorder
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderid=").append(orderid);
        sb.append(", userid=").append(userid);
        sb.append(", serialnumber=").append(serialnumber);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", hotelname=").append(hotelname);
        sb.append(", roomid=").append(roomid);
        sb.append(", roomnumber=").append(roomnumber);
        sb.append(", facilityname=").append(facilityname);
        sb.append(", coverurl=").append(coverurl);
        sb.append(", remark=").append(remark);
        sb.append(", totalprice=").append(totalprice);
        sb.append(", actuallypay=").append(actuallypay);
        sb.append(", paytype=").append(paytype);
        sb.append(", comeformid=").append(comeformid);
        sb.append(", bookdate=").append(bookdate);
        sb.append(", paystatus=").append(paystatus);
        sb.append(", orderstatus=").append(orderstatus);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", operatorname=").append(operatorname);
        sb.append(", operatorip=").append(operatorip);
        sb.append(", operatorremark=").append(operatorremark);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}