package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券对单独用户中间表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponsigleuser")
public class ZlCouponsigleuser implements Serializable {
    /**
     * zl_couponrule表ID
     */
    private Integer couponruleid;

    /**
     * zl_wxuser表id
     */
    private Long userid;

}