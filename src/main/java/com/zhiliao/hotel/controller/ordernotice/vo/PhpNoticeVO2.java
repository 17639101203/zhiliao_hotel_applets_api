package com.zhiliao.hotel.controller.ordernotice.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-23 18:16
 **/
@Data
public class PhpNoticeVO2 implements Serializable {

    /**
     * 接收者（用户）的 openid
     */
    @NotNull(message = "touser不能为空")
    @NotBlank(message = "touser不能为空")
    private String touser;

    /**
     * 所需下发的订阅模板id
     */
    private String template_id;

    /**
     * 模板内容
     */
    private Object data;

    /**
     * 点击模板卡片后的跳转页面(非必填)
     */
    private String page;

    /**
     * 跳转小程序类型(非必填)
     */
    private String miniprogram_state;

    /**
     * 语言类型(非必填)
     */
    private String lang;

}
