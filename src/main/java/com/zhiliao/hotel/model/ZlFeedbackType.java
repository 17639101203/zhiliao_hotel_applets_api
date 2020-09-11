package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 反馈类型表
 *
 * @author null
 * @date 2020-08-18
 */
@Data
@Table(name = "zl_feedbacktype")
public class ZlFeedbackType implements Serializable {
    /**
     * 反馈类型ID
     */
    private Integer feedbacktypeid;

    /**
     * 反馈类型名称
     */
    private String feedbacktypename;

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