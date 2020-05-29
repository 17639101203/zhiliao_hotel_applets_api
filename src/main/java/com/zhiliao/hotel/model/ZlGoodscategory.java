package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 酒店超市商品分类表
 *
 * @author xiehuiyi
 * @date 2020-04-26
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlGoodscategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Integer goodscategoryid;

    /**
     * 分类名称:酒水/酒店/零食...
     */
    private String categoryname;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 分类状态 0未启用;1启用
     */
    private Byte categorystatus;

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