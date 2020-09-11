package com.zhiliao.hotel.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 商家地址库
 *
 * @author null
 * @date 2020-09-07
 */
@Data
public class ZlSupplierAddress implements Serializable {
    /**
     * 地址ID
     */
    private Integer addressid;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 是否默认:0否,1是
     */
    private Byte isdefault;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作IP
     */
    private String operatorip;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 更新时间
     */
    private Integer updatedate;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 备注
     */
    private String remark;

}