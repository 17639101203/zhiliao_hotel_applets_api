package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_coupon")
public class ZlCoupon implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 优惠券名称
     */
    private String title;

    /**
     * 优惠券类型 默认0，1：满减券 2:折扣券 3:代金券
     */
    private Byte type;

    /**
     * 优惠券面额；Type:1生效
     */
    private BigDecimal price;

    /**
     * 消费门槛金额；Type:1生效
     */
    private BigDecimal useminprice;

    /**
     * Type:2 时，该字段起作用，代表折扣
     */
    private Float discount;

    /**
     * Type:3时，该字段起作用，代表代金金额
     */
    private BigDecimal pricereplace;

    /**
     * 创建人
     */
    private String createperson;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作人IP
     */
    private String operatorip;

    /**
     * 状态(0:关闭 1:开启)
     */
    private Byte status;

    /**
     * 删除状态(0:正常 1:删除)
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     *
     */
    private Integer updatedate;

    /**
     * 规则名称
     */
    @Transient
    private String ruletitle;

    /**
     * zl_couponrule表ID，优惠券规则ID
     */
    @Transient
    private Integer couponruleid;

    /**
     * 有效期：结束时间(如果为自领用日起算的券，换算结束时间)
     */
    @Transient
    private Integer endtime;

}