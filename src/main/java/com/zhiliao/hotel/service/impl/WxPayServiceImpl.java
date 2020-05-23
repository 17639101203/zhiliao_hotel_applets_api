package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.myOrder.ZlOrderController;
import com.zhiliao.hotel.controller.myOrder.param.WxPayRefundParam;
import com.zhiliao.hotel.controller.myOrder.util.IpUtils;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.util.StringUtils;
import com.zhiliao.hotel.controller.myOrder.config.WxPayConfig;
import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailVO;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.WxPayService;
import com.zhiliao.hotel.service.ZlOrderService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
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
                response.put("package", "prepay_id=" + prepayId);
                //生成的随机字符串
                String nonceStr = StringUtils.getRandomStringByLength(32);
                response.put("nonceStr", nonceStr);
                response.put("appId", WxPayConfig.appid);
                response.put("signType", WxPayConfig.SIGNTYPE);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonceStr + "&prepayId=prepay_id=" + prepayId + "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);
            }
        }

        return response;
    }


    @Override
    public String wxPayReturn(String out_trade_no) {
        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("appid", WxPayConfig.appid);
        queryParams.put("mch_id", WxPayConfig.mch_id);
        queryParams.put("out_trade_no", out_trade_no); //商户订单号
        queryParams.put("nonce_str", nonce_str);

        // 除去数组中的空值和签名参数
        queryParams = PayUtil.paraFilter(queryParams);
        String prestr = PayUtil.createLinkString(queryParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String sign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<nonce_str>" + nonce_str + "</trade_type>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        //调用统一下单接口，并接受返回的结果
        String result = PayUtil.httpRequest(WxPayConfig.orderquery_url, "POST", xml);

        // 将解析结果存储在HashMap中
        Map map = null;
        try {
            map = PayUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断返回结果
        String return_code = (String) map.get("return_code");

        if ("SUCCESS".equals(return_code)) {
            String result_code = (String) map.get("result_code");
            if ("SUCCESS".equals(result_code)) {
                String trade_state = (String) map.get("trade_state");//订单状态
                if ("SUCCESS".equals(trade_state)) {  //支付成功
                    return "支付成功";
                } else if ("CLOSED".equals(trade_state)) {//交易关闭
                    return "交易关闭";
                } else if ("USERPAYING".equals(trade_state)) {//支付中
                    return "支付中";
                } else if ("PAYERROR".equals(trade_state)) {//支付失败
                    return "支付失败";
                }
            }
        }
        return "订单状态不可识别,请重新查询!";
    }

    @Autowired
    ZlOrderService zlOrderService;

    @Override
    public Map<String, Object> wxPayRefund(WxPayRefundParam wxPayRefundParam) {

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        //定义返回值结果字符串
        String resultStr = "";

        //定义返回值map集合
        Map<String, Object> resultMap = new HashMap<>();

        //生成的退款单号字符串
        String out_refund_no = StringUtils.getRandomStringByLength(64);

        String out_trade_no = wxPayRefundParam.getOut_trade_no();
        Integer refund_fee = wxPayRefundParam.getRefund_fee();
        Integer total_fee = wxPayRefundParam.getTotal_fee();

        //查询下单商品信息
        List<OrderDetailVO> orderDetailVOList = zlOrderService.getOrderDetail(out_trade_no);
        //判断退款金额参数是否正常
        BigDecimal totalPrice = null;
        for (OrderDetailVO orderDetailVO : orderDetailVOList) {
            totalPrice = totalPrice.add(orderDetailVO.getPrice().multiply(BigDecimal.valueOf(orderDetailVO.getGoodsCount())));
        }
        if (totalPrice.intValue() == total_fee && refund_fee < totalPrice.intValue()) {
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WxPayConfig.appid);
            packageParams.put("mch_id", WxPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("out_refund_no", out_refund_no);
            packageParams.put("out_trade_no", out_trade_no); //商户订单号
            packageParams.put("refund_fee", String.valueOf(refund_fee)); //退款金额
            packageParams.put("total_fee", String.valueOf(total_fee)); //支付金额,这边需要转成字符串类型,否则后面的签名会失败

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名
            String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据
            String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                    + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<out_refund_no>" + out_refund_no + "</out_refund_no>"
                    + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                    + "<refund_fee>" + refund_fee + "</refund_fee>"
                    + "<total_fee>" + total_fee + "</total_fee>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";
            //调用退款方法
            try {
                String jsonStr = doRefund(WxPayConfig.refund_url, xml);
                Map<String, Object> map = PayUtil.stringToMap(jsonStr);
                String return_code = (String) map.get("return_code");
                if ("SUCCESS".equals(return_code)) {
                    logger.info("调用退款方法成功,订单号:", out_trade_no, "退款单号:", out_refund_no);
                    resultStr = "调用退款方法成功";
                    resultMap.put("resultStr", resultStr);
                    resultMap.put("map", map);
                    return resultMap;
                } else {
                    logger.info("报文为空,订单号:", out_trade_no, "退款单号:", out_refund_no);
                    resultStr = "报文为空";
                    resultMap.put("resultStr", resultStr);
                    return resultMap;
                }
            } catch (Exception e) {
                logger.info("调用退款方法出错,订单号:", out_trade_no);
                resultStr = "调用退款方法出错";
                resultMap.put("resultStr", resultStr);
                return resultMap;
            }
        } else {
            logger.info("订单金额参数非法,订单号:", out_trade_no);
            resultStr = "订单金额参数非法";
            resultMap.put("resultStr", resultStr);
            return resultMap;
        }
    }

    //申请退款方法
    public String doRefund(String url, String data) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream is = new FileInputStream(new File("C:\\Users\\Admin\\Desktop\\CertTrustChainWX.p7b"));
        try {
            keyStore.load(is, "********证书密码*******".toCharArray());
        } finally {
            is.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(
                keyStore,
                "********证书密码*******".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER
        );
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
