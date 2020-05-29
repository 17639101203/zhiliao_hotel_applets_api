package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店超市商品sku
 *
 * @author xiehuiyi
 * @date 2020-05-29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlGoodssku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Integer skuid;

    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 规格ID
     */
    private Integer propertyid;

    /**
     * 原价
     */
    private BigDecimal originalprice;

    /**
     * 现价
     */
    private BigDecimal currentprice;

    /**
     * 库存
     */
    private Integer stockcount;

    /**
     * 销量
     */
    private Integer soldcount;

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