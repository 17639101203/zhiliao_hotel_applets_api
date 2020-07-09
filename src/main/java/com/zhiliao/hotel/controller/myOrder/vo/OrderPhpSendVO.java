package com.zhiliao.hotel.controller.myOrder.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhiliao.hotel.common.exception.JsonObjectSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-24 14:31
 **/
@Data
public class OrderPhpSendVO implements Serializable {

    /**
     * 来源
     */
//    @JsonSerialize(using = JsonObjectSerializer.class)
    private String form;

    /**
     * 主题
     */
//    @JsonSerialize(using = JsonObjectSerializer.class)
    private String channel;

    /**
     * 信息
     */
//    @JsonSerialize(using = JsonObjectSerializer.class)
    private Object message;

}
