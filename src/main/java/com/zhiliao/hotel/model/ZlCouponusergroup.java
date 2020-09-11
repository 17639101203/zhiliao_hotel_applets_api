package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 优惠券用户群组中间表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_couponusergroup")
public class ZlCouponusergroup implements Serializable {

    /**
     * zl_couponrule表ID
     */
    private Integer couponruleid;

    /**
     * zl_userdrawgroup表id

     */
    private Integer userdrawgroupid;

}