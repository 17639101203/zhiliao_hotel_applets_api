package com.zhiliao.hotel.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-18 17:40
 **/
@Configuration
public class MultipartConfigElementConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

}
