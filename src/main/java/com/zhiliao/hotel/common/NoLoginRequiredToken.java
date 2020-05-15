package com.zhiliao.hotel.common;

import java.lang.annotation.*;

/**
 * Created by xiegege on 2020/5/12.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoLoginRequiredToken {
    boolean required() default true;
}