package com.zhiliao.hotel.common;

import java.lang.annotation.*;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-04 14:46
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlashToken {
    boolean required() default true;
}
