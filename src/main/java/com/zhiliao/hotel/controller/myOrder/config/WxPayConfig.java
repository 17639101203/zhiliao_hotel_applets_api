package com.zhiliao.hotel.controller.myOrder.config;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-04-28 10:01
 **/
public class WxPayConfig {

    //小程序appid
    public static final String appid = "wx440f446a4f7569d4";
    //微信支付的商户id
    public static final String mch_id = "1519878851";
    //微信支付的商户密钥
    public static final String key = "abac3a3507c3e9d1f9d3b863c651d333";

    //支付成功后的服务器回调url,此处进行手动回调
    public static final String notify_url = "https://www.zlkj.com/pay/autoPayReturn";

    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信查询订单接口地址
    public static final String orderquery_url = "https://api.mch.weixin.qq.com/pay/orderquery";
    //微信退款接口地址
    public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

}
