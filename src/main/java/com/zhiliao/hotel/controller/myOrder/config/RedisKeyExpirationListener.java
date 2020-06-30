package com.zhiliao.hotel.controller.myOrder.config;

import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.service.ZlOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlOrderService zlOrderService;

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
        //通过字符串切割,获取到订单号
        String[] arrayStr = expiredKey.split("_");
        String out_trade_no = arrayStr[arrayStr.length - 1];
        //更改redis相关数据
        zlOrderService.autoCancelOrder(out_trade_no);
    }

    /**********************************以上是redis过期key配置,以下是redis发布订阅配置*******************************************/

    @Bean
    MessageListenerAdapter messageListener() {
        //abstract methods overwrite
        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
            System.out.println("Message received: " + message.toString());
        });
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(RedisKeyConstant.TOPIC_ROOMSERVICE);
    }

}
