package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券酒店中间表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponhotel")
public class ZlCouponhotel implements Serializable {

    /**
     * zl_couponrule表ID
     */
    private Integer couponruleid;

    /**
     * zl_hotel表id
     */
    private Integer hotelid;

}