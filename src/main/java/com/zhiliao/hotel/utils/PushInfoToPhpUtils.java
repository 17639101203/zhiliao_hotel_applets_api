package com.zhiliao.hotel.utils;

import com.alibaba.fastjson.JSON;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 10:34
 **/
@Component
public class PushInfoToPhpUtils {

    @Autowired
    private static StringRedisTemplate stringRedisTemplate;

    public static void pushInfoToPhp(String channel, Object object) {
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(channel);
        orderPhpSendVO.setMessage(object);
        String orderStr = JSON.toJSONString(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(channel, orderStr);
    }

}
