package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商酒店分润配置表
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_supplierhotelprofitrate")
public class ZlSupplierHotelProfitrate implements Serializable {
    /**
     * 供应商酒店分润ID
     */
    private Integer supplierhotelprofitrateid;

    /**
     * 关联关系ID
     */
    private Integer recid;

    /**
     * 供应商分润比例
     */
    private BigDecimal supplierrate;

    /**
     * 酒店分润比例
     */
    private BigDecimal hotelrate;

    /**
     * 生效时间
     */
    private Integer effectdate;

    /**
     * 失效时间
     */
    private Integer invaliddate;

    /**
     * 立即生效 0=有生效时间 1=立即生效
     */
    private Byte effectimmediate;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作ID
     */
    private String operatorip;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 
     */
    private Integer updatedate;

    /**
     * 
     */
    private Integer createdate;

}