package com.zhiliao.hotel.controller.myOrder.vo;

import com.zhiliao.hotel.model.ZlOrderDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class OrderList{
    
    /**
     * 用户ID
     */
    private Long userid;
    
    /**
     * 酒店名
     */
    private String hotelname;
    
    /**
     * 实际支付金额
     */
    private BigDecimal actuallypay;
    
    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4挂账
     */
    private Byte paytype;
    
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
    
    private List<ZlOrderDetail> zlOrderDetailList;
    
    public Long getUserid(){
        return userid;
    }
    
    public void setUserid(Long userid){
        this.userid=userid;
    }
    
    public String getHotelname(){
        return hotelname;
    }
    
    public void setHotelname(String hotelname){
        this.hotelname=hotelname;
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
    
    public List<ZlOrderDetail> getZlOrderDetailList(){
        return zlOrderDetailList;
    }
    
    public void setZlOrderDetailList(List<ZlOrderDetail> zlOrderDetailList){
        this.zlOrderDetailList=zlOrderDetailList;
    }
    
}
