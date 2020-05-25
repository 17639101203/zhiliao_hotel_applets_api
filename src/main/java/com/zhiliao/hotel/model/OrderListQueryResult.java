package com.zhiliao.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class OrderListQueryResult implements Serializable{
    
    /**
     * 订单ID
     */
    private Long orderid;
    
    /**
     * 用户ID
     */
    private Long userid;
    
    /**
     * 父订单编号
     */
    private String parentorderserialno;
    
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
     * 第一张商品图片地址
     */
    private String goodscoverurl;
    
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
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4挂账
     */
    private Byte paytype;
    
    /**
     * 配送地址(省市区合并完的详细地址), 不为空时表示土特产需要配送
     */
    private String deliveryaddress;
    
    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;
    
    /**
     * 快递ID
     */
    private Integer expressid;
    
    /**
     * 物流编号
     */
    private String tracknumber;
    
    /**
     * 支付状态：0无须支付;1:待支付;2已支付
     */
    private Byte paystatus;
    
    /**
     * 订单状态:-2报损;-1:已取消;0待确认;1已确认/已下单;2 已发货；3已签收/已完成;4最终完成(不能被操作)
     */
    private Byte orderstatus;
    
    /**
     * 退款状态:-2退款被驳回;-1用户取消退款;1审核中;2退款中;3已退款
     */
    private Byte refundstatus;
    
    /**
     * 退款发起次数
     */
    private Byte refundcount;
    
    /**
     * 优惠券ID
     */
    private Integer couponid;
    
    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;
    
    /**
     * 删除状态:0正常;1删除;2用户删除(用户端不显示)
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
     * 优惠券金额
     */
    private BigDecimal couponcash;
    
    /**
     * 配送金额
     */
    private BigDecimal sendcash;
    
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
    
    private List<ZlOrderDetail> zlOrderDetail;
    
    public Long getOrderid(){
        return orderid;
    }
    
    public void setOrderid(Long orderid){
        this.orderid=orderid;
    }
    
    public Long getUserid(){
        return userid;
    }
    
    public void setUserid(Long userid){
        this.userid=userid;
    }
    
    public String getParentorderserialno(){
        return parentorderserialno;
    }
    
    public void setParentorderserialno(String parentorderserialno){
        this.parentorderserialno=parentorderserialno;
    }
    
    public String getOrderserialno(){
        return orderserialno;
    }
    
    public void setOrderserialno(String orderserialno){
        this.orderserialno=orderserialno;
    }
    
    public Integer getHotelid(){
        return hotelid;
    }
    
    public void setHotelid(Integer hotelid){
        this.hotelid=hotelid;
    }
    
    public String getHotelname(){
        return hotelname;
    }
    
    public void setHotelname(String hotelname){
        this.hotelname=hotelname;
    }
    
    public Integer getRoomid(){
        return roomid;
    }
    
    public void setRoomid(Integer roomid){
        this.roomid=roomid;
    }
    
    public String getRoomnumber(){
        return roomnumber;
    }
    
    public void setRoomnumber(String roomnumber){
        this.roomnumber=roomnumber;
    }
    
    public String getGoodscoverurl(){
        return goodscoverurl;
    }
    
    public void setGoodscoverurl(String goodscoverurl){
        this.goodscoverurl=goodscoverurl;
    }
    
    public String getRemark(){
        return remark;
    }
    
    public void setRemark(String remark){
        this.remark=remark;
    }
    
    public BigDecimal getTotalprice(){
        return totalprice;
    }
    
    public void setTotalprice(BigDecimal totalprice){
        this.totalprice=totalprice;
    }
    
    public BigDecimal getActuallypay(){
        return actuallypay;
    }
    
    public void setActuallypay(BigDecimal actuallypay){
        this.actuallypay=actuallypay;
    }
    
    public Byte getPaytype(){
        return paytype;
    }
    
    public void setPaytype(Byte paytype){
        this.paytype=paytype;
    }
    
    public String getDeliveryaddress(){
        return deliveryaddress;
    }
    
    public void setDeliveryaddress(String deliveryaddress){
        this.deliveryaddress=deliveryaddress;
    }
    
    public Integer getComeformid(){
        return comeformid;
    }
    
    public void setComeformid(Integer comeformid){
        this.comeformid=comeformid;
    }
    
    public Integer getExpressid(){
        return expressid;
    }
    
    public void setExpressid(Integer expressid){
        this.expressid=expressid;
    }
    
    public String getTracknumber(){
        return tracknumber;
    }
    
    public void setTracknumber(String tracknumber){
        this.tracknumber=tracknumber;
    }
    
    public Byte getPaystatus(){
        return paystatus;
    }
    
    public void setPaystatus(Byte paystatus){
        this.paystatus=paystatus;
    }
    
    public Byte getOrderstatus(){
        return orderstatus;
    }
    
    public void setOrderstatus(Byte orderstatus){
        this.orderstatus=orderstatus;
    }
    
    public Byte getRefundstatus(){
        return refundstatus;
    }
    
    public void setRefundstatus(Byte refundstatus){
        this.refundstatus=refundstatus;
    }
    
    public Byte getRefundcount(){
        return refundcount;
    }
    
    public void setRefundcount(Byte refundcount){
        this.refundcount=refundcount;
    }
    
    public Integer getCouponid(){
        return couponid;
    }
    
    public void setCouponid(Integer couponid){
        this.couponid=couponid;
    }
    
    public Short getBelongmodule(){
        return belongmodule;
    }
    
    public void setBelongmodule(Short belongmodule){
        this.belongmodule=belongmodule;
    }
    
    public Boolean getIsdelete(){
        return isdelete;
    }
    
    public void setIsdelete(Boolean isdelete){
        this.isdelete=isdelete;
    }
    
    public Integer getCreatedate(){
        return createdate;
    }
    
    public void setCreatedate(Integer createdate){
        this.createdate=createdate;
    }
    
    public Integer getUpdatedate(){
        return updatedate;
    }
    
    public void setUpdatedate(Integer updatedate){
        this.updatedate=updatedate;
    }
    
    public BigDecimal getCouponcash(){
        return couponcash;
    }
    
    public void setCouponcash(BigDecimal couponcash){
        this.couponcash=couponcash;
    }
    
    public BigDecimal getSendcash(){
        return sendcash;
    }
    
    public void setSendcash(BigDecimal sendcash){
        this.sendcash=sendcash;
    }
    
    public String getOperatorname(){
        return operatorname;
    }
    
    public void setOperatorname(String operatorname){
        this.operatorname=operatorname;
    }
    
    public String getOperatorip(){
        return operatorip;
    }
    
    public void setOperatorip(String operatorip){
        this.operatorip=operatorip;
    }
    
    public String getOperatorremark(){
        return operatorremark;
    }
    
    public void setOperatorremark(String operatorremark){
        this.operatorremark=operatorremark;
    }
    
    public List<ZlOrderDetail> getZlOrderDetail(){
        return zlOrderDetail;
    }
    
    public void setZlOrderDetail(List<ZlOrderDetail> zlOrderDetail){
        this.zlOrderDetail=zlOrderDetail;
    }
    
}
