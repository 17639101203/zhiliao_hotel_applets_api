package com.zhiliao.hotel.controller.goods.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-19 17:54
 **/
@Configuration
public class ElasticSearchConfig {

    /**
     * 防止netty的bug
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
