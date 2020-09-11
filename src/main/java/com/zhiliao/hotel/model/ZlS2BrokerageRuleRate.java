package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * S2合伙人佣金规则
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_s2brokeragerulerate")
public class ZlS2BrokerageRuleRate implements Serializable {
    /**
     * 自增ID
     */
    private Integer rateid;

    /**
     * 佣金规则ID
     */
    private Integer ruleid;

    /**
     * 所属模块 1便利店;2餐饮服务;3情趣用品;4土特产 统一设置佣金时为0
     */
    private Short belongmodule;

    /**
     * 佣金率
     */
    private BigDecimal rate;

    /**
     * 酒店达到数量
     */
    private Integer reachhotelcount;

    /**
     * 时间段开始
     */
    private Integer hotelstaytimebegin;

    /**
     * 时间段结束
     */
    private Integer hotelstaytimeend;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0正常；1删除
     */
    private Boolean isdelete;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作人
     */
    private String operatorip;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

}