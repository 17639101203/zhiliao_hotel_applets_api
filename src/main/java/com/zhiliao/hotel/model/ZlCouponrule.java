package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券规则表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponrule")
public class ZlCouponrule implements Serializable {
    /**
     * 规则ID
     */
    private Integer id;

    /**
     * zl_coupon表ID，所属券的具体规则
     */
    private Integer couponid;

    /**
     * 规则名称
     */
    private String title;

    /**
     * 有效期限类型(0：区间时间 1:自领取日起N天)
     */
    private Byte effecttype;

    /**
     * 有效期：开始时间
     */
    private Integer starttime;

    /**
     * 有效期：结束时间
     */
    private Integer endtime;

    /**
     * 有效期：领取日起N天
     */
    private Integer days;

    /**
     * 投放方式(0：默认 1：主动领取 2：定向发放)
     */
    private Byte puttype;

    /**
     * 投放数量(0:不限量 N：N张)
     */
    private Integer putnum;

    /**
     * 领取时间：开始时间
     */
    private Integer gainstarttime;

    /**
     * 领取时间：结束时间
     */
    private Integer gainendtime;

    /**
     * 领取次数限制
     */
    private Byte gainnum;

    /**
     * 适用领取人(0：默认全部注册用户 1:指定用户群体 2:指定个别用户)
     */
    private Byte suitusergroup;

    /**
     * 使用酒店状态(0：默认全部酒店 1：指定酒店)
     */
    private Byte suithotel;

    /**
     * 使用模块(0：所有模块 1：指定模块<多模块>)
     */
    private Byte suitgoodscategray;

    /**
     * 状态(-1：失效 0：未开启 1：正常开启)
     */
    private Byte status;

    /**
     * 删除状态(0:正常 1:删除)
     */
    private Boolean isdelete;

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
     * 创建时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

}