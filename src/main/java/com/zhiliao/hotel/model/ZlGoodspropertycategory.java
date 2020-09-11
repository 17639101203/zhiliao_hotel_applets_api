package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 酒店超市商品规格（属性）分类
 *
 * @author xiehuiyi
 * @date 2020-05-29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zl_goodspropertycategory")
public class ZlGoodspropertycategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格ID
     */
    private Integer categoryid;

    /**
     * 规格名称
     */
    private String categoryname;

    /**
     * 商品分类ID
     */
    private Integer goodscategoryid;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 操作人员
     */
    private String operatorusername;

    /**
     * 操作人员IP
     */
    private String operatorip;

}