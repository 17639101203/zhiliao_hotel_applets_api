package com.zhiliao.hotel.controller.ordernotice.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-23 14:56
 **/
@Data
public class PhpNoticeVO {

    /**
     * 接收者（用户）的 openid
     */
    private String touser;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    private MpTemplateMsgVO mp_template_msg;

}
