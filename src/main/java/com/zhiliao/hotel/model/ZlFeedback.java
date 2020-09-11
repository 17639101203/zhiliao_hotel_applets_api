package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 反馈表
 *
 * @author null
 * @date 2020-08-18
 */
@Data
@Table(name = "zl_feedback")
public class ZlFeedback implements Serializable {
    /**
     * 反馈ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer feedbackid;

    /**
     * 反馈类型ID,zl_feedbacktype
     */
    private Integer feedbacktype;

    /**
     * 反馈人ID
     */
    private Long feedbackpersonid;

    /**
     * 反馈人
     */
    private String feedbackperson;

    /**
     * 反馈人联系电话
     */
    private String feedbackpersonmobile;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 是否删除 0：正常 1：删除
     */
    private Boolean isdelete;

    /**
     * 反馈时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈图片，可多图
     */
    private String imgsurl;

}