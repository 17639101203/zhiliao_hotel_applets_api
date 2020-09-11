package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商超订单操作表
 *
 * @author null
 * @date 2020-09-03
 */
@Data
@Table(name = "zl_shoplog")
public class ZlShoplog implements Serializable {
    /**
     * 主键ID
     */
    private Long shopid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 付款金额
     */
    private BigDecimal orderprice;

    /**
     * 退款金额
     */
    private BigDecimal refundprice;

    /**
     * 下单时间
     */
    private Integer booktime;

    /**
     * 操作时间
     */
    private Integer operatetime;

    /**
     * 超时时间(单位:分钟)
     */
    private Short losetime;

    /**
     * 使用时间(单位:秒)
     */
    private Integer usetime;

    /**
     * 操作状态-1:退款;1完成;2接单
     */
    private Byte operatetype;

    /**
     * 是否超时
     */
    private Boolean islose;

    /**
     * 订单类型：1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

}