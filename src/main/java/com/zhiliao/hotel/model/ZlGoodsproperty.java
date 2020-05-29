package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 酒店超市商品规格值（属性）
 *
 * @author xiehuiyi
 * @date 2020-05-29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlGoodsproperty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格ID
     */
    private Integer propertyid;

    /**
     * 规格名称
     */
    private String propertyname;

    /**
     *
     */
    private String imgurl;

    /**
     * 属性分类ID
     */
    private Integer categoryid;

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
}