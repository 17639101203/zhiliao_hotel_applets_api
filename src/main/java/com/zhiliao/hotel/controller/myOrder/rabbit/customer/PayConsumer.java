package com.zhiliao.hotel.controller.myOrder.rabbit.customer;

import com.zhiliao.hotel.controller.myOrder.ZlOrderController;
import com.zhiliao.hotel.controller.myOrder.rabbit.producer.PayProducer;
import com.zhiliao.hotel.service.WxPayService;
import com.zhiliao.hotel.service.impl.WxPayServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-07 14:50
 **/
@Component
public class PayConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderController.class);

    @Autowired
    WxPayService wxPayService;

    @Autowired
    WxPayServiceImpl wxPayServiceImpl;

    @Autowired
    PayProducer payProducer;

//    int count = 0;

    @RabbitListener(queues = "payOrderNotice")
    public void payOrderNoticeReceive(Message message) {
        Map<String, String> map = (Map<String, String>) SerializationUtils.deserialize(message.getBody());
        String sign = map.get("sign");
        String out_trade_no = map.get("out_trade_no");
        if (StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(out_trade_no)) {
            Map<String, Object> noticeMap = wxPayService.wxPayReturn(sign, out_trade_no);
            if (noticeMap != null) {
                Boolean bool = (Boolean) noticeMap.get("bool");
                noticeMap.put("out_trade_no", out_trade_no);
                if (bool) {
                    payProducer.successNotice(noticeMap);
                } else {
                    /*count++;
                    if (count > 5) {
                        throw new RuntimeException("查询支付结果失败!");
                    }*/
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    payProducer.payOrderNotice(sign, out_trade_no);
                }
            }
        }
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(value = "successNotice"))
    public void successNoticeReceive2(Message message) {
        Map<String, Object> map = (Map<String, Object>) SerializationUtils.deserialize(message.getBody());
        //更改数据库信息及状态
        wxPayServiceImpl.updateTable(map);
    }

}
