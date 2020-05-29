package com.zhiliao.hotel.controller.myOrder.vo;

import com.zhiliao.hotel.model.ZlOrderDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class OrderDetailsReturn{
    
    /**
     * 订单编号
     */
    private String orderserialno;
    
    /**
     * 酒店名
     */
    private String hotelname;
    
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
     * 优惠券金额
     */
    private BigDecimal couponcash;
    
    /**
     * 配送金额
     */
    private BigDecimal sendcash;
    
    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;
    
    /**
     * 送达时间;默认0表示尽快送达
     */
    private Integer deliverydate;
    
    /**
     * 下单时间
     */
    private Integer createdate;
    
    /**
     * 支付/取消时间
     */
    private Integer updatedate;
    
    private List<ZlOrderDetail> zlOrderDetailList;
    
    public String getOrderserialno(){
        return orderserialno;
    }
    
    public void setOrderserialno(String orderserialno){
        this.orderserialno=orderserialno;
    }
    
    public String getHotelname(){
        return hotelname;
    }
    
    public void setHotelname(String hotelname){
        this.hotelname=hotelname;
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
    
    public Short getBelongmodule(){
        return belongmodule;
    }
    
    public void setBelongmodule(Short belongmodule){
        this.belongmodule=belongmodule;
    }
    
    public Integer getDeliverydate(){
        return deliverydate;
    }
    
    public void setDeliverydate(Integer deliverydate){
        this.deliverydate=deliverydate;
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
    
    public List<ZlOrderDetail> getZlOrderDetailList(){
        return zlOrderDetailList;
    }
    
    public void setZlOrderDetailList(List<ZlOrderDetail> zlOrderDetailList){
        this.zlOrderDetailList=zlOrderDetailList;
    }
    
    @Override
    public String toString(){
        return "OrderDetailInfoReturn{"+
                "orderserialno='"+orderserialno+'\''+
                ", hotelname='"+hotelname+'\''+
                ", remark='"+remark+'\''+
                ", totalprice="+totalprice+
                ", actuallypay="+actuallypay+
                ", paytype="+paytype+
                ", deliveryaddress='"+deliveryaddress+'\''+
                ", expressid="+expressid+
                ", tracknumber='"+tracknumber+'\''+
                ", paystatus="+paystatus+
                ", orderstatus="+orderstatus+
                ", refundstatus="+refundstatus+
                ", refundcount="+refundcount+
                ", couponcash="+couponcash+
                ", sendcash="+sendcash+
                ", belongmodule="+belongmodule+
                ", deliverydate="+deliverydate+
                ", createdate="+createdate+
                ", updatedate="+updatedate+
                ", zlOrderDetailList="+zlOrderDetailList+
                '}';
    }
    
}
