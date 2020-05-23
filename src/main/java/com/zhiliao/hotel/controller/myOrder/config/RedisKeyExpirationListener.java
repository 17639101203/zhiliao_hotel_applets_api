package com.zhiliao.hotel.controller.myOrder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 09:58
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    RedisTemplate redisTemplate;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * redis key失效，监听
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 业务处理 , 注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        Object o = redisTemplate.opsForValue().get(expiredKey);
        System.err.println(expiredKey);
        System.err.println(o);
//        RuleUtils.executeActionByKey(expiredKey);
    }
}
