package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myOrder.ZlOrderController;
import com.zhiliao.hotel.controller.myOrder.dto.WxPayRefundQueryDTO;
import com.zhiliao.hotel.controller.myOrder.params.WxPayRefundParam;
import com.zhiliao.hotel.controller.myOrder.util.IpUtils;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.util.StringUtils;
import com.zhiliao.hotel.controller.myOrder.config.WxPayConfig;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPayShortInfoVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.WxPayService;
import com.zhiliao.hotel.service.ZlGoodsService;
import com.zhiliao.hotel.service.ZlOrderProfitService;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.IPUtils;
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
import javax.validation.constraints.NotBlank;
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

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @Autowired
    private ZlRefundRecordMapper zlRefundRecordMapper;

    @Autowired
    private ZlRefundCheckRecordMapper zlRefundCheckRecordMapper;

    @Autowired
    private ZlOrderService zlOrderService;

    @Autowired
    private ZlOrderParentMapper zlOrderParentMapper;

    @Autowired
    private ZlGoodsService zlGoodsService;

    @Autowired
    private ZlOrderProfitService zlOrderProfitService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private ZlShoplogMapper zlShoplogMapper;

    @Override
    public Map<String, String> wxPay(Long userID, String body, BigDecimal total_fee, String out_trade_no, HttpServletRequest request) {

        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userID);
        List<ZlWxuser> userInfoList = zlWxuserMapper.select(zlWxuser);
        String openid = userInfoList.get(0).getWxopenid();

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        //获取本机的IP地址
        String spbill_create_ip = IPUtils.getIpAddr(request);
//        String spbill_create_ip = "192.168.110.85";

        BigDecimal multiply = total_fee.multiply(BigDecimal.valueOf(100));
//        String total_feeStr = String.valueOf(multiply);
        int total_feeStr = multiply.intValue();
//        System.out.println(total_feeStr);

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", WxPayConfig.appid);
        packageParams.put("mch_id", WxPayConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no); //商户订单号
        packageParams.put("total_fee", total_feeStr + ""); //支付金额,这边需要转成字符串类型,否则后面的签名会失败
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", WxPayConfig.notify_url);
        packageParams.put("trade_type", WxPayConfig.TRADETYPE);
        packageParams.put("openid", openid);

        // 除去数组中的空值和签名参数
//        packageParams = PayUtil.paraFilter(packageParams);
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
//        String prestr = PayUtil.createLinkString(packageParams);

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
//        String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();
        String mysign = null;
        try {
            mysign = PayUtil.generateSignature(packageParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        packageParams.put("sign", mysign);
        boolean bool = false;
        try {
            bool = PayUtil.isSignatureValid(packageParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            logger.info("第一次签名验证成功...");
        } else {
            logger.info("第一次签名验证失败...");
            throw new BizException("第一次签名验证失败...");
        }

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
                + "<total_fee>" + total_feeStr + "</total_fee>"
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
        Map<String, String> response = new HashMap<String, String>();
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

//                String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonceStr + "&prepayId=prepay_id=" + prepayId + "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
//                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();

                String secordSign = null;
                try {
                    secordSign = PayUtil.generateSignature(response, WxPayConfig.key);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.put("sign", secordSign);
                boolean secondBool = false;
                try {
                    secondBool = PayUtil.isSignatureValid(response, WxPayConfig.key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (secondBool) {
                    logger.info("第二次签名验证成功...");
                } else {
                    logger.info("第二次签名验证失败...");
                    throw new BizException("第二次签名验证失败...");
                }

                logger.info("=======================第二次签名：" + secordSign + "=====================");
                response.put("paySign", secordSign);
                response.put("out_trade_no", out_trade_no);
                return response;
            } else {
                logger.info("调用统一下单接口失败...");
                throw new BizException("调用统一下单接口失败...");
            }
        } else {
            logger.info("调用统一下单接口失败...");
            throw new BizException("调用统一下单接口失败...");
        }
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

        String sign = null;
        try {
            sign = PayUtil.generateSignature(queryParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        queryParams.put("sign", sign);
        boolean bool = false;
        try {
            bool = PayUtil.isSignatureValid(queryParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            logger.info("签名验证成功...");
        } else {
            logger.info("签名验证失败...");
            throw new BizException("签名验证失败...");
        }


        //拼接查询支付订单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        //调用查询支付订单接口，并接受返回的结果
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
                    logger.info("支付成功,订单号:", out_trade_no);
                    //订单金额相同,更改数据库状态
                    zlGoodsService.updateGoodsCount(out_trade_no);
                    zlOrderService.updateOrder(out_trade_no);
                    //进行分润
                    zlOrderProfitService.setUpOrderProfit(out_trade_no);
                    //订单通知php
                    zlOrderService.OrderNoticeToPhp(out_trade_no);
                    return "支付成功";
                } else if ("REFUND".equals(trade_state)) {//转入退款
                    logger.info("转入退款,订单号:", out_trade_no);
                    return "转入退款";
                } else if ("NOTPAY".equals(trade_state)) {//未支付
                    logger.info("未支付,订单号:", out_trade_no);
                    return "未支付";
                } else if ("CLOSED".equals(trade_state)) {//已关闭
                    logger.info("已关闭,订单号:", out_trade_no);
                    return "已关闭";
                } else if ("REVOKED".equals(trade_state)) {//已撤销（刷卡支付）
                    logger.info("已撤销,订单号:", out_trade_no);
                    return "已撤销（刷卡支付）";
                } else if ("USERPAYING".equals(trade_state)) {//用户支付中
                    logger.info("用户支付中,订单号:", out_trade_no);
                    return "用户支付中";
                } else if ("PAYERROR".equals(trade_state)) {//支付失败
                    logger.info("支付失败,订单号:", out_trade_no);
                    return "支付失败";
                }
            }
        }
        return "订单状态不可识别,请重新查询!";
    }

    @Override
    public String wxPayReturnQuery(WxPayRefundQueryDTO wxPayRefundQueryDTO) {

        Long orderid = wxPayRefundQueryDTO.getOrderid();
        String out_trade_no = wxPayRefundQueryDTO.getOut_trade_no();

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("appid", WxPayConfig.appid);
        queryParams.put("mch_id", WxPayConfig.mch_id);
        queryParams.put("nonce_str", nonce_str);
        queryParams.put("out_trade_no", out_trade_no); //商户订单号

        String sign = null;
        try {
            sign = PayUtil.generateSignature(queryParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        queryParams.put("sign", sign);
        boolean bool = false;
        try {
            bool = PayUtil.isSignatureValid(queryParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            logger.info("签名验证成功...");
        } else {
            logger.info("签名验证失败...");
            throw new BizException("签名验证失败...");
        }


        //拼接查询退款订单状态接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";

        System.out.println("调用查询退款接口地址 请求XML数据：" + xml);

        //调用查询退款订单状态接口地址，并接受返回的结果
        String result = PayUtil.httpRequest(WxPayConfig.refundquery_url, "POST", xml);

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
                String refund_status = (String) map.get("refund_status_0");//订单状态
                if ("SUCCESS".equals(refund_status)) {  //退款成功
                    logger.info("退款成功,订单号:", out_trade_no);
                    //当前时间
                    Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);
//                    updateRefundStatus(orderid);
                    ZlOrder zlOrder = new ZlOrder();
                    zlOrder.setOrderserialno(out_trade_no);
                    zlOrder = zlOrderMapper.selectOne(zlOrder);
                    ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
                    zlRefundRecord.setOrderid(wxPayRefundQueryDTO.getOrderid());
                    zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
                    //更改数据库商品库存
                    zlGoodsService.updateGoodsCountReturn(zlOrder.getOrderid());
                    //更改数据库订单状态
                    wxPayService.updateRefundStatus(zlOrder.getOrderid());
                    //添加退款记录
                    ZlShoplog zlShoplog = new ZlShoplog();
                    zlShoplog.setOrderid(zlOrder.getOrderid());
                    zlShoplog.setHotelid(zlOrder.getHotelid());
                    zlShoplog.setOrderprice(zlOrder.getActuallypay());
                    zlShoplog.setRefundprice(zlRefundRecord.getRefundmoney());
                    zlShoplog.setBooktime(zlOrder.getCreatedate());
                    zlShoplog.setOperatetime(currertTime);
                    zlShoplog.setUsetime(currertTime - zlOrder.getCreatedate());
                    zlShoplog.setOperatetype((byte) -1);
                    zlShoplog.setBelongmodule(zlOrder.getBelongmodule());
                    zlShoplogMapper.insertSelective(zlShoplog);

                    return "退款成功";
                } else if ("REFUNDCLOSE".equals(refund_status)) {//退款关闭
                    logger.info("退款关闭,订单号:", out_trade_no);
                    return "退款关闭";
                } else if ("PROCESSING".equals(refund_status)) {//退款处理中
                    logger.info("退款处理中,订单号:", out_trade_no);
                    return "退款处理中";
                } else if ("CHANGE".equals(refund_status)) {//退款异常
                    logger.info("退款异常,订单号:", out_trade_no);
                    return "退款异常";
                }
            }
        }
        return "订单状态不可识别,请重新查询!";
    }

    @Override
    public void updateRefundStatus(Long orderid) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        zlOrderMapper.updateRefundStatus3(orderid, currertTime);

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(orderid);
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
        if (zlRefundRecord != null) {
            zlRefundRecordMapper.updateRefundStatus3(orderid, currertTime);
            ZlRefundCheckRecord zlRefundCheckRecord = new ZlRefundCheckRecord();
            zlRefundCheckRecord.setOrderid(orderid);
            zlRefundCheckRecord.setRefundrecid(zlRefundRecord.getRecid());
            zlRefundCheckRecord.setChecktype((byte) 3);
            zlRefundCheckRecord.setCheckstatus((byte) 4);
            zlRefundCheckRecord.setCreatedate(currertTime);
            zlRefundCheckRecord.setUpdatedate(currertTime);
            zlRefundCheckRecordMapper.insertSelective(zlRefundCheckRecord);
        }

    }

    @Override
    public void signForOrder(Long orderId) {
        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(orderId);
        zlOrder = zlOrderMapper.selectOne(zlOrder);
        if (zlOrder == null) {
            throw new BizException("订单id有误!");
        }

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlOrderMapper.signForOrder(orderId, currertTime);
    }

    @Override
    public Map<String, Object> wxPayRefund(WxPayRefundParam wxPayRefundParam) {

        Map<String, Object> orderSerialNoMap = new HashMap<>();

        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(wxPayRefundParam.getOrderid());
        zlOrder = zlOrderMapper.selectOne(zlOrder);
        if (zlOrder == null) {
            throw new BizException("订单id有误,请重新输入!");
        }
        orderSerialNoMap.put("orderid", wxPayRefundParam.getOrderid());

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(wxPayRefundParam.getOrderid());
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
        if (zlRefundRecord == null) {
            throw new BizException("该订单没有申请记录,请提交申请后再吊起退款!");
        }

        String out_trade_no = null;
        BigDecimal allActuallypay = new BigDecimal(0);
        if (zlOrder.getIsparentpay()) {
            out_trade_no = zlOrder.getParentorderserialno();
            ZlOrderParent zlOrderParent = new ZlOrderParent();
            zlOrderParent.setParentorderserialno(out_trade_no);
            zlOrderParent = zlOrderParentMapper.selectOne(zlOrderParent);
            allActuallypay = allActuallypay.add(zlOrderParent.getAllactuallypay());
        } else {
            out_trade_no = zlOrder.getOrderserialno();
            allActuallypay = allActuallypay.add(zlOrder.getActuallypay());
        }

        //生成的随机字符串
        String nonce_str = StringUtils.getRandomStringByLength(32);

        //定义返回值结果字符串
        String resultStr = "";

        //定义返回值map集合
        Map<String, Object> resultMap = new HashMap<>();

        //生成的退款单号字符串
        String out_refund_no = zlRefundRecord.getRefundserialno();
//        String out_refund_no = "BL5062477166245106";

        BigDecimal refund_fee = wxPayRefundParam.getRefund_fee();
        BigDecimal actuallypay = zlOrder.getActuallypay();

        if (!(actuallypay.subtract(wxPayRefundParam.getRefund_fee()).intValue() == 0)) {
            throw new BizException("退款金额有误,请重新填写...");
        }

        refund_fee = refund_fee.multiply(BigDecimal.valueOf(100));
        int refund_feeIn = refund_fee.intValue();
        allActuallypay = allActuallypay.multiply(BigDecimal.valueOf(100));
        int allActuallypayIn = allActuallypay.intValue();

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", WxPayConfig.appid);
        packageParams.put("mch_id", WxPayConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", out_trade_no); //商户订单号
        packageParams.put("out_refund_no", out_refund_no); //商户退款单号
        packageParams.put("total_fee", allActuallypayIn + ""); //支付金额,这边需要转成字符串类型,否则后面的签名会失败
        packageParams.put("refund_fee", refund_feeIn + ""); //退款金额
        packageParams.put("notify_url", WxPayConfig.refund_notify_url); //退款回调地址


        orderSerialNoMap.put("out_trade_no", out_trade_no);
        orderSerialNoMap.put("out_refund_no", out_refund_no);

        String mysign = null;
        try {
            //生成签名
            mysign = PayUtil.generateSignature(packageParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        packageParams.put("sign", mysign);
        boolean bool = false;
        try {
            bool = PayUtil.isSignatureValid(packageParams, WxPayConfig.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            logger.info("签名验证成功...");
        } else {
            logger.info("签名验证失败...");
            throw new BizException("签名验证失败...");
        }


        //拼接退款接口使用的xml数据
        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<out_refund_no>" + out_refund_no + "</out_refund_no>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<notify_url>" + WxPayConfig.refund_notify_url + "</notify_url>"
                + "<refund_fee>" + refund_feeIn + "</refund_fee>"
                + "<total_fee>" + allActuallypayIn + "</total_fee>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";


        //调用退款接口，并接受返回的结果
        String result = null;
        try {
            result = doRefund(WxPayConfig.refund_url, xml);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("调用退款接口失败!");
        }
//        String result = PayUtil.httpRequest(WxPayConfig.refund_url, "POST", xml);

        // 将解析结果存储在HashMap中
        Map map = null;
        try {
            map = PayUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) map.get("return_code");

        if ("SUCCESS".equals(return_code)) {
            String result_code = (String) map.get("result_code");
            if ("SUCCESS".equals(result_code)) {
                logger.info("调用退款方法成功,订单号:", out_trade_no, "退款单号:", out_refund_no);
                return orderSerialNoMap;
            } else {
                logger.info("调用退款方法失败,订单号:", out_trade_no, "退款单号:", out_refund_no);
                throw new BizException("调用退款方法失败!");
            }
        } else {
            logger.info("调用退款方法失败,订单号:", out_trade_no, "退款单号:", out_refund_no);
            throw new BizException("调用退款方法失败!");
        }
    }

    //申请退款方法
    public String doRefund(String url, String data) throws Exception {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //这里自行实现我是使用数据库配置将证书上传到了服务器可以使用 FileInputStream读取本地文件
        FileInputStream inputStream = new FileInputStream(new File("E:\\zlgj\\gitlabxcx\\zhiliao_hotel_applets_api.git\\src\\main\\java\\com\\zhiliao\\hotel\\config\\apiclient_cert.p12"));
        try {
            //这里写密码..默认是你的MCHID
            keyStore.load(inputStream, WxPayConfig.mch_id.toCharArray());
        } finally {
            inputStream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                //这里也是写密码的
                .loadKeyMaterial(keyStore, WxPayConfig.mch_id.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                //接受到返回信息
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
