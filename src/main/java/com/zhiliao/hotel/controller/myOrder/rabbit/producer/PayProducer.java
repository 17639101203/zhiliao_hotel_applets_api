package com.zhiliao.hotel.controller.myOrder.rabbit.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-07 14:50
 **/
@Component
public class PayProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //查询支付结果
    /*public void payOrderNotice(String sign, String out_trade_no) {
        Map<String, String> map = new HashMap<>();
        map.put("sign", sign);
        map.put("out_trade_no", out_trade_no);

        rabbitTemplate.convertAndSend("payOrderNotice", map);
    }
*/
    //查询支付结果
    public void payOrderNotice(String sign, String out_trade_no) {
        Map<String, String> map = new HashMap<>();
        map.put("sign", sign);
        map.put("out_trade_no", out_trade_no);
        rabbitTemplate.convertAndSend("orderNotice_exchange", "payOrderNotice", map, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", 3000);
                return message;
            }
        });
    }

    //支付结果为SUCCESS,更改数据库信息及状态
    public void successNotice(Map<String, Object> noticeMap) {
        rabbitTemplate.convertAndSend("successNotice", noticeMap);
    }

}
