package com.zhiliao.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-23 16:03
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Test
    public void test1(){
        System.out.println((int)new Date().getTime());
    }
}
