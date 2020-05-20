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
     * 第一张商品图片地址
     */
    private String goodscoverurl;
    
    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Byte ordertype;
    
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
     * 订单状态：-1:已取消;0待确认;1已确认/已下单;2 已发货；3已签收/已完成;4最终完成(不能被操作)
     */
    private Byte orderstatus;
    
    /**
     * 退款状态:-2退款被驳回;-1用户取消退款;1审核中;2退款中;3已退款
     */
    private Byte refundstatus;
    
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
     * 退款发起次数
     */
    private Byte refundcount;
    
    /**
     * 优惠券ID
     */
    private Integer couponid;
    
    private static final long serialVersionUID=1L;
    
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
    
    public String getOrderserialno(){
        return orderserialno;
    }
    
    public void setOrderserialno(String orderserialno){
        this.orderserialno=orderserialno==null ? null : orderserialno.trim();
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
        this.hotelname=hotelname==null ? null : hotelname.trim();
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
        this.roomnumber=roomnumber==null ? null : roomnumber.trim();
    }
    
    public String getGoodscoverurl(){
        return goodscoverurl;
    }
    
    public void setGoodscoverurl(String goodscoverurl){
        this.goodscoverurl=goodscoverurl==null ? null : goodscoverurl.trim();
    }
    
    public Byte getOrdertype(){
        return ordertype;
    }
    
    public void setOrdertype(Byte ordertype){
        this.ordertype=ordertype;
    }
    
    public String getRemark(){
        return remark;
    }
    
    public void setRemark(String remark){
        this.remark=remark==null ? null : remark.trim();
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
        this.deliveryaddress=deliveryaddress==null ? null : deliveryaddress.trim();
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
        this.tracknumber=tracknumber==null ? null : tracknumber.trim();
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
    
    public String getOperatorname(){
        return operatorname;
    }
    
    public void setOperatorname(String operatorname){
        this.operatorname=operatorname==null ? null : operatorname.trim();
    }
    
    public String getOperatorip(){
        return operatorip;
    }
    
    public void setOperatorip(String operatorip){
        this.operatorip=operatorip==null ? null : operatorip.trim();
    }
    
    public String getOperatorremark(){
        return operatorremark;
    }
    
    public void setOperatorremark(String operatorremark){
        this.operatorremark=operatorremark==null ? null : operatorremark.trim();
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
    
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
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
        sb.append(", goodscoverurl=").append(goodscoverurl);
        sb.append(", ordertype=").append(ordertype);
        sb.append(", remark=").append(remark);
        sb.append(", totalprice=").append(totalprice);
        sb.append(", actuallypay=").append(actuallypay);
        sb.append(", paytype=").append(paytype);
        sb.append(", deliveryaddress=").append(deliveryaddress);
        sb.append(", comeformid=").append(comeformid);
        sb.append(", expressid=").append(expressid);
        sb.append(", tracknumber=").append(tracknumber);
        sb.append(", paystatus=").append(paystatus);
        sb.append(", orderstatus=").append(orderstatus);
        sb.append(", refundstatus=").append(refundstatus);
        sb.append(", operatorname=").append(operatorname);
        sb.append(", operatorip=").append(operatorip);
        sb.append(", operatorremark=").append(operatorremark);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", refundcount=").append(refundcount);
        sb.append(", couponid=").append(couponid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    
}
