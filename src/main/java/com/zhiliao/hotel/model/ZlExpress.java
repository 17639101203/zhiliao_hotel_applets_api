package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 快递表
 *
 * @author null
 * @date 2020-08-15
 */
@Data
@Table(name = "zl_express")
public class ZlExpress implements Serializable {
    /**
     * 快递ID
     */
    private Short expressid;

    /**
     * 快递名
     */
    private String expressname;

    /**
     * 快递名简称
     */
    private String expressabbrname;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

}