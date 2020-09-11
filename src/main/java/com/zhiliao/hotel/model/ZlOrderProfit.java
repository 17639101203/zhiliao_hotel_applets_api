package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单分润表
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_orderprofit")
public class ZlOrderProfit implements Serializable {
    /**
     * 订单分润ID
     */
    private Long orderprofitid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 合伙人ID 酒店没有归属合伙人为0
     */
    private Integer s2partnerid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallypay;

    /**
     * 合伙人分润ID 没有生效的为0
     */
    private Integer s2partnerrateid;

    /**
     * 合伙人分润率
     */
    private BigDecimal s2partnerrate;

    /**
     * 供应商酒店分润ID 没有生效的为0
     */
    private Integer supplierhotelprofitid;

    /**
     * 供应商分润率
     */
    private BigDecimal supplierrate;

    /**
     * 酒店分润率
     */
    private BigDecimal hotelrate;

    /**
     * 合伙人分润金额
     */
    private BigDecimal s2partnerratemoney;

    /**
     * 供应商分润金额
     */
    private BigDecimal supplierratemoney;

    /**
     * 酒店分润金额
     */
    private BigDecimal hotelratemoney;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

}