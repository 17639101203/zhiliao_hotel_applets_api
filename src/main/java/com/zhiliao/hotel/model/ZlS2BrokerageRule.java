package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * S2合伙人佣金规则
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_s2brokeragerule")
public class ZlS2BrokerageRule implements Serializable {
    /**
     * 佣金规则ID
     */
    private Integer ruleid;

    /**
     * 佣金规则名称
     */
    private String rulename;

    /**
     * 佣金率0:统一佣金率;1按模块佣金率
     */
    private Byte ruletype;

    /**
     * 合伙人ID
     */
    private Integer partnerid;

    /**
     * 生效日期
     */
    private Integer effectivedate;

    /**
     * 失效时间
     */
    private Integer invaliddate;

    /**
     * 状态:0待发布(草稿);1:预发布;2:发布中;3:已失效
     */
    private Byte rulestatus;

    /**
     * 操作人员
     */
    private String operatorname;

    /**
     * 操作人员IP
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

    /**
     * 佣金规则描述
     */
    private String ruledescription;

}