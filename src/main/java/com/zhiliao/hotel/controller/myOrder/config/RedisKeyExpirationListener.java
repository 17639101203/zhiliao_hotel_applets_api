package com.zhiliao.hotel.controller.myOrder.config;

import com.alibaba.fastjson.JSON;
import com.zhiliao.hotel.common.constant.OrderNoticeConstant;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.service.ZlCouponService;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 09:58
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    @Value("${weChat.appid}")
    private String APP_ID;
    @Value("${weChat.secret}")
    private String SECRET;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlOrderService zlOrderService;

    @Autowired
    private ZlCouponService zlCouponService;

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

        //订单过期处理
        if (expiredKey.startsWith(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG)) {
            //通过字符串切割,获取到订单号
            String[] arrayStr = expiredKey.split("_");
            String out_trade_no = arrayStr[arrayStr.length - 1];
            //更改redis相关数据
            zlOrderService.autoCancelOrder(out_trade_no);
        }

        //小程序发布订阅AssessToken过期处理
        if (expiredKey.equals("XcxAccessToken")) {
            String params = "grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;
            logger.info("请求微信接口获取access_token,链接为" + OrderNoticeConstant.getAccessToken + "?" + params);
            String resultJson = HttpUtil.sendGet(OrderNoticeConstant.getAccessToken, params);
            if (StringUtils.isNoneBlank(resultJson)) {
                Map map = JSON.parseObject(resultJson);
                String accessToken = (String) map.get("access_token");
                redisTemplate.opsForValue().set("XcxAccessToken", accessToken, RedisKeyConstant.ACCESSTOKEN, TimeUnit.SECONDS);
            }
        }

        //用户所领优惠券过期处理
        if (expiredKey.startsWith(RedisKeyConstant.COUPON_USER_FLAG)) {
            //通过字符串切割,获取到订单号
            String[] arrayStr = expiredKey.split("_");
            String couponUserId = arrayStr[arrayStr.length - 1];
            //更改redis相关数据
            zlCouponService.autoCouponUserStatus(couponUserId);
        }
    }

    /**********************************以上是redis过期key配置,以下是redis发布订阅配置*******************************************/

    /*@Bean
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
        return new ChannelTopic("");
    }*/

}
