package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 客房服务商品分类表
 *
 * @author xiehuiyi
 * @date 2020-05-29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlServicegoodscategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Integer goodscategoryid;

    /**
     * 分类名称:男生用品、女生用品……
     */
    private String categoryname;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 所属模块 1:客房服务;
     */
    private Short belongmodule;

    /**
     * 分类状态 0未启用;1启用
     */
    private Byte categorystatus;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    private Byte checkstatus;

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