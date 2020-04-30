package com.zhiliao.hotel.controller.hotelfacility.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *  酒店设施预定信息
 * @author null
 * @date 2020-04-28
 */
public class FacilityOrderVo implements Serializable {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单编号
     */
    private String serialNumber;

    /**
     * 酒店ID
     */
    private Integer hotelId;

    /**
     * 酒店名
     */
    private String hotelName;

    /**
     * 房间ID
     */
    private Integer roomId;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 设施名称
     */
    private String facilityName;

    /**
     * 设施封面图片地址
     */
    private String coverUrl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallyPay;

    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它
     */
    private Byte payType;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeFormId;

    /**
     * 预定时间
     */
    private String bookDate;

    /**
     * 0无须支付;1:待支付;2已支付
     */
    private Byte payStatus;

    /**
     * -1:已取消;0等待确认;1已确认
     */
    private Byte orderStatus;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isDelete;
    

    /**
     * 下单时间
     */
    private Integer createDate;

    /**
     * 支付/取消时间
     */
    private Integer updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_hotelfacilityorder
     *
     * @mbg.generated Tue Apr 28 09:49:18 CST 2020
     */
    private static final long serialVersionUID = 1L;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getActuallyPay() {
        return actuallyPay;
    }

    public void setActuallyPay(BigDecimal actuallyPay) {
        this.actuallyPay = actuallyPay;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Integer getComeFormId() {
        return comeFormId;
    }

    public void setComeFormId(Integer comeFormId) {
        this.comeFormId = comeFormId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}