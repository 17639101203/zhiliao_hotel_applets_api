package com.zhiliao.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author null
 * @date 2020-04-15
 */
public class ZlOrder implements Serializable {
    /**
     * 订单ID
     */
    private Long orderID;

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 订单编号
     */
    private String orderSerialNo;

    /**
     * 酒店ID
     */
    private Integer hotelID;

    /**
     * 酒店名
     */
    private String hotelName;

    /**
     * 房间ID
     */
    private Integer roomID;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 第一张商品/设施图片地址
     */
    private String goodsCoverUrl;

    /**
     * 1:商品订单;2设施订单;3清扫订单;4报修订单
     */
    private Integer orderType;

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
    private Integer payType;

    /**
     * 配送地址(省市区合并完的详细地址), 不为空时表示土特产需要配送
     */
    private String deliveryAddress;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeFormID;

    /**
     * 快递ID
     */
    private Integer expressID;

    /**
     * 物流编号
     */
    private String trackNumber;

    /**
     * 支付状态：0无须支付;1:待支付;2已支付
     */
    private Integer payStatus;

    /**
     * 订单状态：-1:已取消;0待确认;1已确认;2 已发货；3已签收/已完成
     */
    private Byte orderStatus;

    /**
     * 删除状态:0正常;1删除;
     */
    private Integer isDelete;

    /**
     * 操作员
     */
    private String operatorName;

    /**
     * 操作员IP
     */
    private String operatorIP;

    /**
     * 操作员备注
     */
    private String operatorRemark;

    /**
     * 下单时间
     */
    private Integer createDate;

    /**
     * 支付/取消时间
     */
    private Integer updateDate;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getOrderSerialNo() {
        return orderSerialNo;
    }

    public void setOrderSerialNo(String orderSerialNo) {
        this.orderSerialNo = orderSerialNo;
    }

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

    public String getGoodsCoverUrl() {
        return goodsCoverUrl;
    }

    public void setGoodsCoverUrl(String goodsCoverUrl) {
        this.goodsCoverUrl = goodsCoverUrl;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getComeFormID() {
        return comeFormID;
    }

    public void setComeFormID(Integer comeFormID) {
        this.comeFormID = comeFormID;
    }

    public Integer getExpressID() {
        return expressID;
    }

    public void setExpressID(Integer expressID) {
        this.expressID = expressID;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorIP() {
        return operatorIP;
    }

    public void setOperatorIP(String operatorIP) {
        this.operatorIP = operatorIP;
    }

    public String getOperatorRemark() {
        return operatorRemark;
    }

    public void setOperatorRemark(String operatorRemark) {
        this.operatorRemark = operatorRemark;
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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
