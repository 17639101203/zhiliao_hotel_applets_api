package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 供应商酒店关系表
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_supplierhotel")
public class ZlSupplierHotel implements Serializable {
    /**
     * 关联关系ID
     */
    private Integer recid;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作ID
     */
    private String operatorip;

    /**
     * 
     */
    private Integer updatedate;

    /**
     * 
     */
    private Integer createdate;

}