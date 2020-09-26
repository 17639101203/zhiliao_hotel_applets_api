package com.zhiliao.hotel.controller.myOrder;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myOrder.config.WxPayConfig;
import com.zhiliao.hotel.controller.myOrder.dto.*;
import com.zhiliao.hotel.controller.myOrder.params.GoodsInfoParam;
import com.zhiliao.hotel.controller.myOrder.params.WxPayRefundParam;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.vo.*;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlExpress;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlRefundCheckRecord;
import com.zhiliao.hotel.model.ZlSupplierAddress;
import com.zhiliao.hotel.service.*;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ·
 */
@Api(tags = "订单接口_姬慧慧")
@RestController
@RequestMapping("order")
public class ZlOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderController.class);

    @Autowired
    private ZlOrderDetailService zlOrderDetailService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private ZlOrderService zlOrderService;

    @Autowired
    private ZlGoodsService zlGoodsService;

    @Autowired
    private ZlOrderProfitService zlOrderProfitService;

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @ApiOperation(value = "我的订单_姬慧慧")
    @UserLoginToken
//    @PassToken
    @PostMapping("allOrder")
    public ReturnString findAllOrder(HttpServletRequest request, @Validated @RequestBody OrderInfoDTO orderInfoDTO) {

        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);

        try {
            orderInfoDTO.setUserId(userId);
            PageInfoResult allOrder = zlOrderService.findAllOrder(orderInfoDTO);
            return new ReturnString(allOrder);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }

    }

    @ApiOperation(value = "订单详情_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "int", required = true, value = "订单id"),
    })
    @GetMapping("detail/{orderId}")
    @UserLoginToken
    @ResponseBody
    public ReturnString findOrderDetail(@PathVariable("orderId") Integer orderId) {

        try {
            OrderDetailsReturn orderDetailsReturn = zlOrderDetailService.findOrder(Long.valueOf(orderId));
            return new ReturnString(orderDetailsReturn);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "提交订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Long", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "Long", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "path", name = "roomNumber", dataType = "String", required = true, value = "房间编号")
    })
    @PostMapping("submitOrder/{hotelID}/{hotelName}/{roomID}/{roomNumber}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString submitOrder(
            HttpServletRequest request,
            @PathVariable("hotelID") Integer hotelID,
            @PathVariable("hotelName") String hotelName,
            @PathVariable("roomID") Integer roomID,
            @PathVariable("roomNumber") String roomNumber,
            @RequestBody Map<String, List<GoodsInfoParam>> goodsInfoParamMap) {
        //封装对象
        HotelBasicVO hotelBasicVO = new HotelBasicVO();
        hotelBasicVO.setHotelID(hotelID);
        hotelBasicVO.setHotelName(hotelName);
        hotelBasicVO.setRoomID(roomID);
        hotelBasicVO.setRoomNumber(roomNumber);
        //获取用户id
        String token = request.getHeader("token");
        Long userID = TokenUtil.getUserId(token);
//        Long userID = 150L;

        try {
            UserGoodsReturn userGoodsReturn = zlOrderService.submitOrder(request,userID, hotelBasicVO, goodsInfoParamMap);
            return new ReturnString(userGoodsReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "微信下单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "body", dataType = "String", required = true, value = "商品描述"),
            @ApiImplicitParam(paramType = "path", name = "total_fee", dataType = "String", required = true, value = "标价金额"),
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("wxPay/{body}/{total_fee}/{out_trade_no}")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPay(
            HttpServletRequest httpServletRequest,
            @PathVariable("body") String body,
            @PathVariable("total_fee") BigDecimal total_fee,
            @PathVariable("out_trade_no") String out_trade_no,
            HttpServletRequest request) {
        //获取用户id
        String token = httpServletRequest.getHeader("token");
        Long userID = TokenUtil.getUserId(token);

        try {
            Map<String, String> response = wxPayService.wxPay(userID, body, total_fee, out_trade_no, request);
            return new ReturnString(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "订单支付状态主动查询终极版接口_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("wxPayReturn/{out_trade_no}")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayReturn(@PathVariable("out_trade_no") String out_trade_no) {

        try {
            String resultString = wxPayService.wxPayReturn(out_trade_no);
            return new ReturnString(0, resultString);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

   /* @ApiOperation(value = "订单支付状态查询接口_姬慧慧")
    @UserLoginToken
    @PostMapping("autoPayReturn")
    @ResponseBody
    public String autoPayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        System.out.println("接收到的报文：" + notityXml);
        // 将解析结果存储在HashMap中
        Map returnMap = null;
        try {
            returnMap = PayUtil.xmlToMap(notityXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) returnMap.get("return_code");

        if ("SUCCESS".equals(return_code)) {

            String resultCode = returnMap.get("result_code").toString();
            if (resultCode.equals("SUCCESS")) {
                String out_trade_no = (String) returnMap.get("out_trade_no");//订单号
                //验证签名是否正确
                Map<String, String> packageParams = PayUtil.paraFilter(returnMap);  //回调验签时需要去除sign和空值参数

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
                    logger.info("签名验证成功...");
                } else {
                    logger.info("签名验证失败...");
                    throw new BizException("签名验证失败...");
                }

                logger.info("------验证微信端传过来的sign参数-----");
                if (!mysign.equals(returnMap.get("sign"))) {
                    logger.info("微信端传过来的sign参数验证不通过,可能为假冒通知!!!");
                    String xml = "<xml>"
                            + "<return_code><![CDATA[FAIL]]</return_code>"
                            + "<return_msg><![CDATA[签名不一致]]></return_msg>"
                            + "</xml>";
                    return xml;
//                    throw new BizException("微信端传过来的sign参数验证不通过,可能为假冒通知!!!");
                }

                //此处对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
                logger.info("微信端传过来的sign参数验证通过");

                //查询该订单下的订单金额信息
                BigDecimal orderActuallyPay = new BigDecimal(0);
                logger.info("------验证微信端传过来的金额参数-----");
                if (out_trade_no.startsWith("SM")) {
                    BigDecimal orderPay = zlOrderService.getOrderActuallyPay(out_trade_no);
                    orderActuallyPay = orderActuallyPay.add(orderPay);
                } else {
                    BigDecimal orderPay = zlOrderService.getOrderActuallyPay2(out_trade_no);
                    orderActuallyPay = orderActuallyPay.add(orderPay);
                }

                orderActuallyPay = orderActuallyPay.multiply(BigDecimal.valueOf(100));
                Integer total_fee = (Integer) returnMap.get("total_fee");

                if (orderActuallyPay.intValue() != total_fee) {
                    logger.info("微信端传过来的金额参数验证不通过,可能为假冒通知!!!");
                    String xml = "<xml>"
                            + "<return_code><![CDATA[FAIL]]</return_code>"
                            + "<return_msg><![CDATA[金额不一致]]></return_msg>"
                            + "</xml>";
                    return xml;
//                    throw new BizException("微信端传过来的金额参数验证不通过,可能为假冒通知!!!");
                }

                logger.info("微信端传过来的金额参数验证通过");

                //订单金额相同,更改数据库状态
                zlGoodsService.updateGoodsCount(out_trade_no);
                zlOrderService.updateOrder(out_trade_no);
                //进行分润
                zlOrderProfitService.setUpOrderProfit(out_trade_no);
                //订单通知php
                zlOrderService.OrderNoticeToPhp(out_trade_no);
                logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                String xml = "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]</return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>"
                        + "</xml>";
                return xml;
            } else {
                String xml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]</return_code>"
                        + "<return_msg><![CDATA[支付通知失败]]></return_msg>"
                        + "</xml>";
                return xml;
            }
        } else {
            String xml = "<xml>"
                    + "<return_code><![CDATA[FAIL]]</return_code>"
                    + "<return_msg><![CDATA[支付通知失败]]></return_msg>"
                    + "</xml>";
            return xml;
        }

    }*/

    /*@ApiOperation(value = "订单支付状态自动回调测试版_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @UserLoginToken
    @PostMapping("autoPayReturnTest/{out_trade_no}")
    @ResponseBody
    public ReturnString autoPayReturnTest(@PathVariable("out_trade_no") String out_trade_no) throws Exception {
        try {
            //订单金额相同,更改数据库状态
            zlGoodsService.updateGoodsCount(out_trade_no);
            zlOrderService.updateOrder(out_trade_no);
            //进行分润
            zlOrderProfitService.setUpOrderProfit(out_trade_no);
            //订单通知php
            zlOrderService.OrderNoticeToPhp(out_trade_no);
            return new ReturnString(0, "支付回调成功!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }*/


    @ApiOperation(value = "用户手动取消订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("cancelOrder/{out_trade_no}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString cancelOrder(@PathVariable("out_trade_no") String out_trade_no) {
        try {
            zlOrderService.cancelOrder(out_trade_no);
            return new ReturnString(0, "已取消");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户签收订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "int", required = true, value = "订单id")
    })
    @PostMapping("signForOrder/{orderId}")
    @UserLoginToken
    @ResponseBody
    public ReturnString signForOrder(@PathVariable("orderId") Integer orderId) {
        try {
            wxPayService.signForOrder(Long.valueOf(orderId));
            return new ReturnString(0, "已签收!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "微信小程序支付退款接口_姬慧慧")
    @PostMapping("wxPayRefund")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayRefund(@Validated @RequestBody WxPayRefundParam wxPayRefundParam) {
        try {
            Map<String, Object> orderSerialNoMap = wxPayService.wxPayRefund(wxPayRefundParam);
            return new ReturnString(orderSerialNoMap);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    /*@ApiOperation(value = "订单退款状态自动回调,包括数据库的相关修改_姬慧慧")
    @UserLoginToken
    @PostMapping("autoRefundReturn")
    @ResponseBody
    public String autoRefundReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        System.out.println("接收到的报文：" + notityXml);
        // 将解析结果存储在HashMap中
        Map returnMap = null;
        try {
            returnMap = PayUtil.xmlToMap(notityXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) returnMap.get("return_code");

        if ("SUCCESS".equals(return_code)) {
            String refund_status = (String) returnMap.get("refund_status");
            if ("SUCCESS".equals(refund_status)) {
                String out_trade_no = (String) returnMap.get("out_trade_no");
                ZlOrder zlOrder = new ZlOrder();
                zlOrder.setOrderserialno(out_trade_no);
                zlOrder = zlOrderMapper.selectOne(zlOrder);

                //更改数据库商品库存
                zlGoodsService.updateGoodsCountReturn(zlOrder.getOrderid());
                //更改数据库订单状态
                wxPayService.updateRefundStatus(zlOrder.getOrderid());
                logger.info("退款回调成功!");
                String xml = "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]</return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>"
                        + "</xml>";
                return xml;
            } else if ("CHANGE".equals(refund_status)) {
                logger.info("退款异常!");
                String xml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]</return_code>"
                        + "<return_msg><![CDATA[退款异常]]></return_msg>"
                        + "</xml>";
                return xml;
//                throw new BizException("退款异常!");
            } else if ("REFUNDCLOSE".equals(refund_status)) {
                logger.info("退款关闭!");
                String xml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]</return_code>"
                        + "<return_msg><![CDATA[退款关闭]]></return_msg>"
                        + "</xml>";
                return xml;
//                throw new BizException("退款关闭!");
            } else {
                logger.info("退款通知失败!");
                String xml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]</return_code>"
                        + "<return_msg><![CDATA[退款通知失败]]></return_msg>"
                        + "</xml>";
                return xml;
            }
        } else {
            logger.info("退款通知失败!");
            String xml = "<xml>"
                    + "<return_code><![CDATA[FAIL]]</return_code>"
                    + "<return_msg><![CDATA[退款通知失败]]></return_msg>"
                    + "</xml>";
            return xml;
        }

    }*/

    /*@ApiOperation(value = "订单退款状态自动回调测试版_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @UserLoginToken
    @PostMapping("autoRefundReturnTest/{out_trade_no}")
    @ResponseBody
    public ReturnString autoRefundReturnTest(@PathVariable("out_trade_no") String out_trade_no) throws Exception {
        try {
            ZlOrder zlOrder = new ZlOrder();
            zlOrder.setOrderserialno(out_trade_no);
            zlOrder = zlOrderMapper.selectOne(zlOrder);

            //更改数据库商品库存
            zlGoodsService.updateGoodsCountReturn(zlOrder.getOrderid());
            //更改数据库订单状态
            wxPayService.updateRefundStatus(zlOrder.getOrderid());
            return new ReturnString(0, "退款回调成功!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }*/

    @ApiOperation(value = "订单退款状态主动查询终极版接口_姬慧慧")
    @PostMapping("wxPayReturnQuery")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayReturnQuery(@Validated @RequestBody WxPayRefundQueryDTO wxPayRefundQueryDTO) {

        try {
            String resultString = wxPayService.wxPayReturnQuery(wxPayRefundQueryDTO);
            return new ReturnString(0, resultString);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户主动删除订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderSerialNo", dataType = "String", required = true, value = "商户订单号"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1便利店;2餐饮服务;3情趣用品;4土特产")
    })
    @PostMapping("userDeleteOrder/{orderSerialNo}/{belongModule}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteOrder(@PathVariable("orderSerialNo") String orderSerialNo, @PathVariable("belongModule") Integer belongModule) {
        try {
            zlOrderService.userDeleteOrder(orderSerialNo, belongModule);
            return new ReturnString(0, "删除订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("删除订单失败!");
        }
    }

    @ApiOperation(value = "用户申请退款_姬慧慧")
    @PostMapping("orderRefundRecord")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString orderRefundRecord(HttpServletRequest request,
                                          @Validated @RequestBody RefundRecordDTO refundRecordDTO) {

        //获取用户id
        String token = request.getHeader("token");
        Long userID = TokenUtil.getUserId(token);
        try {
//            RefundapplyInfoVO refundapplyInfoVO = zlOrderService.orderRefundRecord(request, userID, refundRecordDTO);
            zlOrderService.orderRefundRecord(request, userID, refundRecordDTO);
            return new ReturnString(0, "退款申请提交成功,请等待审核!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户退款申诉_姬慧慧")
    @PostMapping("orderRefundAppeal")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString orderRefundAppeal(HttpServletRequest request, @Validated @RequestBody OrderRefundAppealDTO orderRefundAppealDTO) {

        //获取用户id
        String token = request.getHeader("token");
        Long userID = TokenUtil.getUserId(token);
        try {
            zlOrderService.orderRefundAppeal(request, userID, orderRefundAppealDTO);
            return new ReturnString(0, "申诉提交成功,请等待审核!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户获取快递列表_姬慧慧")
    @GetMapping("getExpressList")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString getExpressList() {

        try {
            List<ZlExpress> zlExpressList = zlOrderService.getExpressList();
            return new ReturnString(zlExpressList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户上传物流信息_姬慧慧")
    @PostMapping("uploadExpressInfo")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString uploadExpressInfo(HttpServletRequest request, @Validated @RequestBody UploadExpressInfoDTO uploadExpressInfoDTO) {

        try {
            zlOrderService.uploadExpressInfo(request, uploadExpressInfoDTO);
            return new ReturnString(0, "物流上传成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户取消退款申请_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "int", required = true, value = "订单id"),
    })
    @PostMapping("cancelRefundapply/{orderId}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString cancelRefundapply(@PathVariable("orderId") Integer orderId) {

        try {
            zlOrderService.cancelRefundapply(Long.valueOf(orderId));
            return new ReturnString(0, "取消退款成功!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户退款协商历史_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "int", required = true, value = "订单id"),
    })
    @PostMapping("orderRefundHistory/{orderId}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString orderRefundHistory(@PathVariable("orderId") Integer orderId) {

        try {
            List<OrderRefundHistoryVO> orderRefundHistoryVOList = zlOrderService.orderRefundHistory(Long.valueOf(orderId));
            return new ReturnString(orderRefundHistoryVOList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "获取供应商详细地址信息_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderId", dataType = "Long", required = true, value = "订单id"),
    })
    @PostMapping("getSupplierAddress/{orderId}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString getSupplierAddress(@PathVariable("orderId") Long orderId) {

        try {
            ZlSupplierAddress zlSupplierAddress = zlOrderService.getSupplierAddress(orderId);
            return new ReturnString(zlSupplierAddress);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

}
