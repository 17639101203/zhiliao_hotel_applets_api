package com.zhiliao.hotel.controller.order.config;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-04-28 10:01
 **/
public class WxPayConfig {

    //小程序appid
    public static final String appid = "";
    //微信支付的商户id
    public static final String mch_id = "";
    //微信支付的商户密钥
    public static final String key = "";

    //支付成功后的服务器回调url,此处进行手动回调
    public static final String notify_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
