package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户群组表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_userdrawgroup")
public class ZlUserdrawgroup implements Serializable {
    /**
     * 用户群组ID
     */
    private Integer usergroupid;

    /**
     * 群组名称
     */
    private String usergroupname;

    /**
     * 群组备注
     */
    private String remark;

    /**
     * 是否删除 0:正常 1:删除
     */
    private Boolean isdelete;

    /**
     * 创建人
     */
    private String createperson;

    /**
     * 操作人
     */
    private String operatorname;

    /**
     * 操作IP
     */
    private String operatorip;

    /**
     * 
     */
    private Integer createdate;

    /**
     * 
     */
    private Integer updatedate;

    /**
     * 群组规则
     */
    private String rule;

}