package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 父订单
 *
 * @author null
 * @date 2020-08-13
 */
@Data
@Table(name = "zl_orderparent")
public class ZlOrderParent implements Serializable {

    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long parentid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 父订单编号
     */
    private String parentorderserialno;

    /**
     * 总价
     */
    private BigDecimal alltotalprice;

    /**
     * 实际支付金额
     */
    private BigDecimal allactuallypay;

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
     * 更新时间
     */
    private Integer updatedate;

}