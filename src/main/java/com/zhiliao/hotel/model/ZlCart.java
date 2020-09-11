package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 购物车表
 *
 * @author xiehuiyi
 * @date 2020-04-14
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zl_cart")
public class ZlCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    private Long cartid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * skuID
     */
    private Integer hotelgoodsskuid;

    /**
     * 商品数量
     */
    private Integer goodscount;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;
}