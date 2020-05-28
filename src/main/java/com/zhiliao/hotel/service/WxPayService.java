package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.myOrder.param.WxPayRefundParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-04-28 10:02
 **/
public interface WxPayService {

    Map<String, Object> wxPay(String openid, String body, Integer total_fee, String out_trade_no, HttpServletRequest request);

    Map<String, Object> wxPayRefund(WxPayRefundParam wxPayRefundParam);

    String wxPayReturn(String out_trade_no);

}
