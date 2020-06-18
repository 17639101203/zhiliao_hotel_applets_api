package com.zhiliao.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.zhiliao.hotel.mapper")
public class ZhiliaoHotelAppletsApplication {

    public static void main(String[] args) {

        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ZhiliaoHotelAppletsApplication.class, args);
    }

//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
//        return factory;
//    }
}