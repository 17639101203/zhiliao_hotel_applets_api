package com.zhiliao.hotel.controller.myOrder;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.myOrder.config.WxPayConfig;
import com.zhiliao.hotel.controller.myOrder.param.WxPayRefundParam;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.WxPayService;
import com.zhiliao.hotel.service.ZlGoodsService;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ·
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("order")
public class ZlOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderController.class);

    @Autowired
    private ZlOrderService orderService;

    @Autowired
    private ZlOrderDetailService orderDetailService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private PayUtil payUtil;

    @Autowired
    private ZlOrderService zlOrderService;

    @Autowired
    private ZlGoodsService zlGoodsService;

    @Autowired
    public ZlOrderController(ZlOrderService orderService, ZlOrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @ApiOperation(value = "我的订单", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderType", dataType = "int", required = false, value = "订单类型：1.商品订单；2.设施订单；3清扫订单；4.报修订单。"),
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：-1.已取消；0.待确认；1.已确认；2.已发货；3.已签收/已完成。"),
            @ApiImplicitParam(paramType = "query", name = "payStatus", dataType = "int", required = false, value = "支付状态：0：无须支付；1：待支付；2：已支付。"),
            @ApiImplicitParam(paramType = "query", name = "payType", dataType = "int", required = false, value = "支付方式：0.无支付方式；1.微信；2.支付宝；3.银行卡；4.其它。"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("all")
    public ReturnString findAllOrder(String token, Integer orderType, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize) {

        try {

            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = orderService.findAllOrder(userId, orderType, orderStatus, payStatus, payType, pageNo, pageSize);
            return new ReturnString(allOrder);

        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }

    }

    @ApiOperation(value = "订单详情")
    @PostMapping("Detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "orderID", value = "订单ID", required = true)
    })
    @UserLoginToken
    @ResponseBody
    public ReturnString findOrderDetail(String token, Long orderID) {
        Long userID = TokenUtil.getUserId(token);
        logger.info("用户ID：" + userID + "，订单ID：" + orderID);
        try {
            ZlOrderDetail orderDetail = orderDetailService.findOrder(userID, orderID);
            return new ReturnString(orderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "订单id")
    })
    @PostMapping("quXiaoOrder/{orderID}")
    @UserLoginToken
    @ResponseBody
    public ReturnString findByjiudianId(String token, @PathVariable Long orderID) {
        try {
            orderService.byOrderId(orderID);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消失败");
        }
    }

    @ApiOperation(value = "酒店超市_提交订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Long", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "Long", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "path", name = "roomNumber", dataType = "String", required = true, value = "房间编号")
    })
    @PostMapping("submitOrder/{hotelID}/{hotelName}/{roomID}/{roomNumber}")
//    @UserLoginToken
    @PassToken
    @ResponseBody
    public ReturnString submitOrder(
            HttpServletRequest httpServletRequest,
            @PathVariable("hotelID") Integer hotelID,
            @PathVariable("hotelName") String hotelName,
            @PathVariable("roomID") Integer roomID,
            @PathVariable("roomNumber") String roomNumber
            , @RequestBody Map<String, List<GoodsInfoVO>> GoodsInfoMap) {
        //封装对象
        HotelBasicVO hotelBasicVO = new HotelBasicVO();
        hotelBasicVO.setHotelID(hotelID);
        hotelBasicVO.setHotelName(hotelName);
        hotelBasicVO.setRoomID(roomID);
        hotelBasicVO.setRoomNumber(roomNumber);
        //获取用户id
        String token = httpServletRequest.getHeader("token");
//        Long userID = TokenUtil.getUserId(token);
        Long userID = System.currentTimeMillis();

        try {
            List<ZlOrder> zlOrderList = orderService.submitOrder(userID, hotelBasicVO, GoodsInfoMap);
            return new ReturnString(zlOrderList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("提交失败");
        }
    }

    @ApiOperation(value = "微信下单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "openid", dataType = "String", required = true, value = "用户标识"),
            @ApiImplicitParam(paramType = "path", name = "body", dataType = "String", required = true, value = "商品描述"),
            @ApiImplicitParam(paramType = "path", name = "total_fee", dataType = "Integer", required = true, value = "标价金额"),
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("wxPay/{openid}/{body}/{total_fee}/{out_trade_no}")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPay(
            String token,
            @PathVariable("openid") String openid,
            @PathVariable("body") String body,
            @PathVariable("total_fee") Integer total_fee,
            @PathVariable("out_trade_no") String out_trade_no,
            HttpServletRequest request) {

        try {
            Map<String, Object> response = wxPayService.wxPay(openid, body, total_fee, out_trade_no, request);
            return new ReturnString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("提交失败");
        }
    }

    @ApiOperation(value = "微信支付账单查询：该接口不做复杂的业务处理，仅根据商户订单号查询订单情况返回支付结果")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")

    })
    @PostMapping("wxPayReturn")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayReturn(String token, @PathVariable("out_trade_no") String out_trade_no) {

        WXPayConfig wxPayConfig = new WXPayConfig() {
            @Override
            public String getAppID() {
                return WxPayConfig.appid;
            }

            @Override
            public String getMchID() {
                return WxPayConfig.mch_id;
            }

            @Override
            public String getKey() {
                return WxPayConfig.key;
            }

            @Override
            public InputStream getCertStream() {
                return null;
            }

            @Override
            public int getHttpConnectTimeoutMs() {
                return 0;
            }

            @Override
            public int getHttpReadTimeoutMs() {
                return 0;
            }
        };

        Map<String, String> result = null;

        try {
            //创建sdk客户端
            WXPay wxPay = new WXPay(wxPayConfig);
            Map<String, String> map = new HashMap<>();
            map.put("out_trade_no", out_trade_no);//商户内部的订单号
            //调用微信的订单查询接口
            result = wxPay.orderQuery(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("调用微信订单查询接口失败");
        }
        String return_code = result.get("return_code");
        String result_code = result.get("result_code");
        String trade_state = result.get("trade_state");//订单状态

        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {

            if ("SUCCESS".equals(trade_state)) {  //支付成功
                return new ReturnString("支付成功");
            } else if ("CLOSED".equals(trade_state)) {//交易关闭
                return new ReturnString("交易关闭");
            } else if ("USERPAYING".equals(trade_state)) {//支付中
                return new ReturnString("支付中");
            } else if ("PAYERROR".equals(trade_state)) {//支付失败
                return new ReturnString("支付失败");
            }
        }
        return new ReturnString("不可识别的微信订单状态");
    }

    @ApiOperation(value = "支付回调")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),

    })
    @UserLoginToken
    @PostMapping("autoPayReturn")
    @ResponseBody
    public ReturnString autoPayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);
        // 将解析结果存储在HashMap中
        Map returnMap = null;
        try {
            returnMap = PayUtil.xmlToMap(notityXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) returnMap.get("return_code");
        String out_trade_no = (String) returnMap.get("out_trade_no");//订单号

        if ("SUCCESS".equals(return_code)) {
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(returnMap);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, WxPayConfig.key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了

            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if (sign.equals(returnMap.get("sign"))) {
                //查询下单商品信息
                List<ZlOrderDetail> zlOrderDetailList = zlOrderService.getOrderDetail(out_trade_no);
                //比较实际付款价格和总价是否一致
                BigDecimal totalPrice = null;
                for (ZlOrderDetail zlOrderDetail : zlOrderDetailList) {
                    totalPrice = totalPrice.add(zlOrderDetail.getPrice().multiply(BigDecimal.valueOf(zlOrderDetail.getGoodscount())));
                }
                Integer total_fee = (Integer) returnMap.get("total_fee");
                if (totalPrice.intValue() == total_fee) {
                    zlGoodsService.updateGoodsCount(zlOrderDetailList);
                    zlOrderService.updateOrder(out_trade_no);
                    logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else {
                    logger.info("订单金额不符,订单号:", out_trade_no);
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单金额不符]]></return_msg>" + "</xml> ";
//                    throw new RuntimeException("订单金额不符");
                }
            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[微信支付回调失败!签名不一致]]></return_msg>" + "</xml> ";
//                throw new RuntimeException("微信支付回调失败!签名不一致");
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();

        return new ReturnString(resXml);

    }

    @ApiOperation(value = "微信支付退款")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token")
    })
    @PostMapping("wxPayRefund")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayRefund(String token, @RequestBody WxPayRefundParam wxPayRefundParam) {
        try {
            Map<String, Object> response = wxPayService.wxPayRefund(wxPayRefundParam);
            return new ReturnString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("支付回调失败");
        }
    }

}
