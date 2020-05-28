package com.zhiliao.hotel.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情表
 *
 * @author null
 * @date 2020-04-15
 */
@Table(name="zl_orderdetail")
public class ZlOrderDetail implements Serializable{
    
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(generator="JDBC")
    private Long orderdetailid;
    
    /**
     * 订单ID
     */
    private Long orderid;
    
    /**
     * 用户ID
     */
    private Long userid;
    
    /**
     * 酒店商品ID
     */
    private Integer hotelgoodsid;
    
    /**
     * 商品名称
     */
    private String goodsname;
    
    /**
     * 商品图片
     */
    private String goodscoverurl;
    
    /**
     * 订单号
     */
    private String orderserialno;
    
    /**
     * 单价
     */
    private BigDecimal price;
    
    /**
     * 数量
     */
    private Integer goodscount;
    
    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;
    
    /**
     * 删除状态:0正常;1删除
     */
    private Boolean isdelete;
    
    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;
    
    /**
     * 下单时间
     */
    private Integer createdate;
    
    /**
     * 支付/取消时间
     */
    private Integer updatedate;
    
    private static final long serialVersionUID=1L;
    
    public Long getOrderdetailid(){
        return orderdetailid;
    }
    
    public void setOrderdetailid(Long orderdetailid){
        this.orderdetailid=orderdetailid;
    }
    
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
    
    public Integer getHotelgoodsid(){
        return hotelgoodsid;
    }
    
    public void setHotelgoodsid(Integer hotelgoodsid){
        this.hotelgoodsid=hotelgoodsid;
    }
    
    public String getGoodsname(){
        return goodsname;
    }
    
    public void setGoodsname(String goodsname){
        this.goodsname=goodsname;
    }
    
    public String getGoodscoverurl(){
        return goodscoverurl;
    }
    
    public void setGoodscoverurl(String goodscoverurl){
        this.goodscoverurl=goodscoverurl;
    }
    
    public String getOrderserialno(){
        return orderserialno;
    }
    
    public void setOrderserialno(String orderserialno){
        this.orderserialno=orderserialno;
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
    public void setPrice(BigDecimal price){
        this.price=price;
    }
    
    public Integer getGoodscount(){
        return goodscount;
    }
    
    public void setGoodscount(Integer goodscount){
        this.goodscount=goodscount;
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
    
    public Boolean getIsuserdelete(){
        return isuserdelete;
    }
    
    public void setIsuserdelete(Boolean isuserdelete){
        this.isuserdelete=isuserdelete;
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
    
    public static long getSerialVersionUID(){
        return serialVersionUID;
    }
    
    @Override
    public String toString(){
        return "ZlOrderDetail{"+
                "orderdetailid="+orderdetailid+
                ", orderid="+orderid+
                ", userid="+userid+
                ", hotelgoodsid="+hotelgoodsid+
                ", goodsname='"+goodsname+'\''+
                ", goodscoverurl='"+goodscoverurl+'\''+
                ", orderserialno='"+orderserialno+'\''+
                ", price="+price+
                ", goodscount="+goodscount+
                ", belongmodule="+belongmodule+
                ", isdelete="+isdelete+
                ", isuserdelete="+isuserdelete+
                ", createdate="+createdate+
                ", updatedate="+updatedate+
                '}';
    }
    
}
