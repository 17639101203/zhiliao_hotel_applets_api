package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户群组与用户关联表
 *
 * @author null
 * @date 2020-08-06
 */
@Data
@Table(name = "zl_groupuser")
public class ZlGroupuser implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * zl_userdrawgroup表ID
     */
    private Integer usergroupid;

    /**
     * zl_wxuser表ID
     */
    private Integer userid;

    /**
     * 
     */
    private Boolean isdelete;

    /**
     * 
     */
    private Integer createdate;

    /**
     * 
     */
    private Integer updatedate;

}