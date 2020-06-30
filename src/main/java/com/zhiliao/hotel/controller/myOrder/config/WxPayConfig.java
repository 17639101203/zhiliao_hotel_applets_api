package com.zhiliao.hotel.controller.myOrder.config;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-04-28 10:01
 **/
public class WxPayConfig {

    //小程序appid
    public static final String appid = "wx6bdd42389ce7ee57";
    //微信支付的商户id
    public static final String mch_id = "1519878851";
    //微信支付的商户密钥
    public static final String key = "481c0c2515d0614141532c604a0dcc93";

    //支付成功后的服务器回调url,此处进行手动回调
//    public static final String notify_url = "https://www.zlkj.com/pay/autoPayReturn";
    public static final String notify_url = "http://204810v5h2.iok.la/";

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
