package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.myOrder.ZlOrderController;
import com.zhiliao.hotel.controller.myOrder.util.IpUtils;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.util.StringUtils;
import com.zhiliao.hotel.controller.order.config.WxPayConfig;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.WxPayService;
import com.zhiliao.hotel.service.ZlGoodsService;
import com.zhiliao.hotel.service.ZlOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-04-28 10:02
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class WxPayServiceImpl implements WxPayService {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderController.class);


    @Override
    public Map<String, Object> wxPay(String openid, String body, Integer total_fee, String out_trade_no, HttpServletRequest request) {

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        //获取本机的IP地址
        String spbill_create_ip = IpUtils.getIpAddr(request);

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", WxPayConfig.appid);
        packageParams.put("mch_id", WxPayConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no); //商户订单号
        packageParams.put("total_fee", String.valueOf(total_fee)); //支付金额,这边需要转成字符串类型,否则后面的签名会失败
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", WxPayConfig.notify_url);
        packageParams.put("trade_type", WxPayConfig.TRADETYPE);
        packageParams.put("openid", openid);

        // 除去数组中的空值和签名参数
        packageParams = PayUtil.paraFilter(packageParams);
        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

        logger.info("=======================第一次签名：" + mysign + "=====================");

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + WxPayConfig.notify_url + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<total_fee>" + String.valueOf(total_fee) + "</total_fee>"
                + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        //调用统一下单接口，并接受返回的结果
        String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

        System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

        // 将解析结果存储在HashMap中
        Map map = null;
        try {
            map = PayUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) map.get("return_code");//返回状态码

        //返回给移动端需要的参数
        Map<String, Object> response = new HashMap<String, Object>();
        if ("SUCCESS".equals(return_code)) {
            String result_code = (String) map.get("result_code");
            if ("SUCCESS".equals(result_code)) {
                // 业务结果
                String prepayId = (String) map.get("prepay_id");//返回的预付单信息
                String nonceStr = (String) map.get("nonce_str");//微信返回的随机字符串
                response.put("prepayId", prepayId);
                response.put("nonceStr", nonceStr);
                response.put("appid", WxPayConfig.appid);
                response.put("signType", WxPayConfig.SIGNTYPE);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonceStr + "&prepayId=" + prepayId + "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);
            }
        }

        return response;
    }

    private PayUtil payUtil;
    private ZlOrderService zlOrderService;
    private ZlGoodsService zlGoodsService;

    @Override
    public Map<String, Object> wxPayReturn(String sign, String out_trade_no) {

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        String xmlBack = "";
        //拼接查询订单接口使用的xml数据

        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";

        //调用查询订单接口，并接受返回的结果
        String result = null;
        try {
            //调用微信的订单查询接口
            result = PayUtil.httpRequest(WxPayConfig.notify_url, "POST", xml);
        } catch (Exception e) {
            logger.info("调用微信订单查询接口失败,订单号为:", out_trade_no);
            e.printStackTrace();
        }

        Map<String, String> returnMap = null;
        try {
            returnMap = PayUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) returnMap.get("return_code");
        String result_code = (String) returnMap.get("result_code");
        String trade_state = (String) returnMap.get("trade_state");//订单状态

        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
            if ("SUCCESS".equals(trade_state)) {  //支付成功
                boolean bool = false;
                try {
                    bool = payUtil.isPayResultNotifySignatureValid(returnMap);
                } catch (Exception e) {
                    // 签名错误，如果数据里没有sign字段，也认为是签名错误
                    logger.error("手机支付回调通知签名错误");
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[手机支付回调通知签名错误]]></return_msg>" + "</xml> ";
                }
                if (bool) {
                    //修改数据库表状态
                    //查询下单商品信息
                    List<ZlOrderDetail> zlOrderDetailList = zlOrderService.getOrderDetail(out_trade_no);

                    //比较实际付款价格和总价是否一致
                    BigDecimal totalPrice = null;
                    for (ZlOrderDetail zlOrderDetail : zlOrderDetailList) {
                        totalPrice = totalPrice.add(zlOrderDetail.getPrice().multiply(BigDecimal.valueOf(zlOrderDetail.getGoodsCount())));
                    }
                    Integer total_fee = Integer.valueOf(returnMap.get("total_fee"));
                    if (totalPrice.intValue() == total_fee / 100) {
                        zlGoodsService.updateGoodsCount(zlOrderDetailList);
                        zlOrderService.updateOrder(out_trade_no);
                        logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        logger.info("订单金额不符,订单号:", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单金额不符]]></return_msg>" + "</xml> ";
                    }
                } else {
                    // 签名错误，如果数据里没有sign字段，也认为是签名错误
                    logger.error("手机支付回调通知签名错误,订单号:", out_trade_no);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[手机支付回调通知签名错误]]></return_msg>" + "</xml> ";
                }
            } else {
                logger.error("手机支付回调通知失败,订单号:", out_trade_no);
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[手机支付回调通知失败]]></return_msg>" + "</xml> ";
            }
        } else {
            logger.error("手机支付回调通知失败");
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[手机支付回调通知失败]]></return_msg>" + "</xml> ";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xmlBack", xmlBack);

        return map;
    }
}
