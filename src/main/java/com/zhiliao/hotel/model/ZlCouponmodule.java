package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券模块中间表(商品大类目中间表)
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponmodule")
public class ZlCouponmodule implements Serializable {

    /**
     * zl_couponrule表id
     */
    private Integer couponruleid;

    /**
     * zl_module表ID,对应field类型blongModule的值
     */
    private Integer moduleid;

}