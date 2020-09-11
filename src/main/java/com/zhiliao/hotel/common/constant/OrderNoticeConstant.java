package com.zhiliao.hotel.common.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-22 15:38
 **/
public class OrderNoticeConstant {

    //获取access_token的链接
    public final static String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token";

    //通知小程序端的链接
    public final static String sendNotice = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send";

    //通知小程序端的链接
    public final static String sendNotice2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    //获取消息模板id的链接
    public final static String gettemplate = "https://api.weixin.qq.com/wxaapi/newtmpl/gettemplate";


}
