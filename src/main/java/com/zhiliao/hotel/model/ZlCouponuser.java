package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券发放记录表(用户领取记录表)
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponuser")
public class ZlCouponuser implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * zl_coupon表ID
     */
    private Integer couponid;

    /**
     * zl_couponrule表ID，优惠券规则ID
     */
    private Integer couponruleid;

    /**
     * zl_wxuser表ID，用户ID
     */
    private Long userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String tel;

    /**
     * 券编码(唯一)
     */
    private String couponno;

    /**
     * 优惠券类型，与zl_coupon表的Type对应
     */
    private Byte coupontype;

    /**
     * 有效期：开始时间（如果为自领用日起算的券，记录领券时间）
     */
    private Integer starttime;

    /**
     * 有效期：结束时间(如果为自领用日起算的券，换算结束时间)
     */
    private Integer endtime;

    /**
     * 使用时间(0:未使用)
     */
    private Integer usetime;

    /**
     * 来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
     */
    private Byte comeformid;

    /**
     * 券状态(-2：已失效 -1：已过期 0：未使用 1：已使用)
     */
    private Byte status;

    /**
     * 删除状态(0：正常 1：删除)
     */
    private Boolean isdelete;

    /**
     * 领取时间
     */
    private Integer createdate;

    /**
     *
     */
    private Integer updatedate;

}