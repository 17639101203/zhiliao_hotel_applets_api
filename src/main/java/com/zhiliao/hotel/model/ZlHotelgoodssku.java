package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 酒店商品
 *
 * @author xiehuiyi
 * @date 2020-05-26
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlHotelgoodssku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Integer hotelgoodsskuid;

    /**
     * 供应商商品SkuID
     */
    private Integer skuid;

    /**
     * 供应商商品ID
     */
    private Integer goodsid;

    /**
     *
     */
    private Integer hotelid;

    /**
     * 库存
     */
    private Integer stockcount;

    /**
     * 销量
     */
    private Integer soldcount;

    /**
     * 虚拟销量
     */
    private Integer virtualsoldcount;

    /**
     * 是否默认0:否；1是
     */
    private Boolean isdefault;

    /**
     * 0正常；1删除
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