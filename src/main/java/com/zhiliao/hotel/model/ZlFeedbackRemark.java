package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 反馈信息备注表
 *
 * @author null
 * @date 2020-08-18
 */
@Data
@Table(name = "zl_feedbackremark")
public class ZlFeedbackRemark implements Serializable {
    /**
     * 
     */
    private Integer feedbackremarkid;

    /**
     * zl_feedback表FeedbackID
     */
    private Integer feedbackid;

    /**
     * 备注人
     */
    private String createperson;

    /**
     * 删除状态 0:正常 1:删除
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

    /**
     * 备注内容
     */
    private String content;

}