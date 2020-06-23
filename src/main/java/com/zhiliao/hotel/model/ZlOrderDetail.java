package com.zhiliao.hotel.model;

import lombok.Data;

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
@Data
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

}
