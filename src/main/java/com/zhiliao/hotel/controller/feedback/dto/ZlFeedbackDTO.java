package com.zhiliao.hotel.controller.feedback.dto;

import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-18 17:58
 **/
@Data
public class ZlFeedbackDTO {

    /**
     * 反馈类型ID,zl_feedbacktype
     */
    @NotNull(message = "反馈类型Id不能为空")
    private Integer feedbacktype;

    /**
     * 反馈内容
     */
    @NotNull(message = "反馈内容不能为空")
    @NotBlank(message = "反馈内容不能为空")
    private String content;

    /**
     * 反馈图片，可多图
     */
    private String imgsurl;

}
