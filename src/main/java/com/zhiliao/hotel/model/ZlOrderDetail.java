package com.zhiliao.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情表
 *
 * @author null
 * @date 2020-04-15
 */
public class ZlOrderDetail implements Serializable {
    /**
     *
     */
    private Long orderdetailid;

    /**
     *
     */
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 商品ID/设施ID (由OrderType决定)
     */
    private Integer goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 商品图片
     */
    private String goodscoverurl;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer goodscount;

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

    
    private static final long serialVersionUID = 1L;

    
    public Long getOrderdetailid() {
        return orderdetailid;
    }

    
    public void setOrderdetailid(Long orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    
    public Long getOrderid() {
        return orderid;
    }

    
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    
    public Long getUserid() {
        return userid;
    }

    
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    
    public Integer getGoodsid() {
        return goodsid;
    }

    
    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    
    public String getGoodsname() {
        return goodsname;
    }

    
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    
    public String getGoodscoverurl() {
        return goodscoverurl;
    }

    
    public void setGoodscoverurl(String goodscoverurl) {
        this.goodscoverurl = goodscoverurl == null ? null : goodscoverurl.trim();
    }

    
    public BigDecimal getPrice() {
        return price;
    }

    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
    public Integer getGoodscount() {
        return goodscount;
    }

    
    public void setGoodscount(Integer goodscount) {
        this.goodscount = goodscount;
    }

    
    public Boolean getIsdelete() {
        return isdelete;
    }

    
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    
    public Integer getCreatedate() {
        return createdate;
    }

    
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    
    public Integer getUpdatedate() {
        return updatedate;
    }

    
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderdetailid=").append(orderdetailid);
        sb.append(", orderid=").append(orderid);
        sb.append(", userid=").append(userid);
        sb.append(", goodsid=").append(goodsid);
        sb.append(", goodsname=").append(goodsname);
        sb.append(", goodscoverurl=").append(goodscoverurl);
        sb.append(", price=").append(price);
        sb.append(", goodscount=").append(goodscount);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
