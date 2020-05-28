package com.zhiliao.hotel.controller.myOrder;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.myOrder.config.WxPayConfig;
import com.zhiliao.hotel.controller.myOrder.param.WxPayRefundParam;
import com.zhiliao.hotel.controller.myOrder.util.PayUtil;
import com.zhiliao.hotel.controller.myOrder.vo.*;
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
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ·
 */
@Api(tags = "订单接口_林生_姬慧慧")
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

    // TODO: 2020/5/25  @PassToken
    @ApiOperation(value = "我的订单_林生", notes = "传入不同的请求参数，查询不同类型的订单。")
    // @UserLoginToken
    @PassToken
    @PostMapping("all")
    public ReturnString findAllOrder(HttpServletRequest request, @RequestBody OrderInfoVO vo) {

        try {

            // TODO: 2020/5/23  userId
            // String token=request.getHeader("token");
            // Long userId=TokenUtil.getUserId(token);
            // logger.info("我的订单，用户ID："+userId);

            Long userId = 1590214659794L;

            logger.info("我的订单，用户ID：" + userId);

            if (vo == null) {
                vo = new OrderInfoVO();
            }
            vo.setUserId(userId);

            logger.error("我的订单，请求参数：" + vo);

            PageInfoResult allOrder = orderService.findAllOrder(vo);
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
            @PathVariable("roomNumber") String roomNumber,
            @RequestBody Map<String, List<GoodsInfoVO>> GoodsInfoMap) {
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
            UserGoodsReturn userGoodsReturn = orderService.submitOrder(userID, hotelBasicVO, GoodsInfoMap);
            return new ReturnString(userGoodsReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("提交失败");
        }
    }

    @ApiOperation(value = "酒店超市_微信下单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "openid", dataType = "String", required = true, value = "用户标识"),
            @ApiImplicitParam(paramType = "path", name = "body", dataType = "String", required = true, value = "商品描述"),
            @ApiImplicitParam(paramType = "path", name = "total_fee", dataType = "Integer", required = true, value = "标价金额"),
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("wxPay/{openid}/{body}/{total_fee}/{out_trade_no}")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPay(
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

    @ApiOperation(value = "酒店超市_订单状态主动查询_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")
    })
    @PostMapping("wxPayReturn")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayReturn(@PathVariable("out_trade_no") String out_trade_no) {

        try {
            String resultString = wxPayService.wxPayReturn(out_trade_no);
            return new ReturnString(resultString);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询订单状态失败,请重新查询!");
        }
    }

    @ApiOperation(value = "酒店超市_订单状态自动回调_姬慧慧")
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

        if ("SUCCESS".equals(return_code)) {
            String out_trade_no = (String) returnMap.get("out_trade_no");//订单号
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(returnMap);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, WxPayConfig.key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了
            //查询数据库中订单状态
            OrderStatusVO orderStatusVO = zlOrderService.getByOrderSerialNo(out_trade_no);
            Byte payStatus = orderStatusVO.getPayStatus();
            Byte orderStatus = orderStatusVO.getOrderStatus();
            if (payStatus == 2 && orderStatus == 1) {
                logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
                if (sign.equals(returnMap.get("sign"))) {

                    //查询该订单下的订单金额信息
                    List<OrderPayShortInfoVO> orderPayShortInfoVOList = zlOrderService.getOrderByOrderSerialNo(out_trade_no);
                    //判断付款金额参数是否正常
                    BigDecimal totalPrice = null;
                    for (OrderPayShortInfoVO orderPayShortInfoVO : orderPayShortInfoVOList) {
                        BigDecimal actuallyPay = orderPayShortInfoVO.getActuallyPay();
                        totalPrice = totalPrice.add(actuallyPay);
                    }
                    Integer total_fee = (Integer) returnMap.get("total_fee");
                    if (totalPrice.intValue() == total_fee) {
                        //订单金额相同,更改数据库状态
                        zlGoodsService.updateGoodsCount(out_trade_no);
                        zlOrderService.updateOrder(out_trade_no);
                        logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        logger.info("订单金额不符,订单号:", out_trade_no);
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单金额不符]]></return_msg>" + "</xml> ";
                    }
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[微信支付回调失败!签名不一致]]></return_msg>" + "</xml> ";
                }
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

    @ApiOperation(value = "酒店超市_手动取消订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1便利店;2餐饮服务;3情趣用品;4土特产")
    })
    @PostMapping("cancelOrder/{out_trade_no}/{belongModule}")
//    @UserLoginToken
    @PassToken
    @ResponseBody
    public ReturnString cancelOrder(@PathVariable("out_trade_no") String out_trade_no, @PathVariable("belongModule") Integer belongModule) {
        try {
            orderService.cancelOrder(out_trade_no, belongModule);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消失败");
        }
    }

    @ApiOperation(value = "微信支付退款,目前仅支持整单退款")
    @PostMapping("wxPayRefund")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayRefund(@RequestBody WxPayRefundParam wxPayRefundParam) {
        try {
            Map<String, Object> response = wxPayService.wxPayRefund(wxPayRefundParam);
            return new ReturnString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("支付回调失败");
        }
    }

}
