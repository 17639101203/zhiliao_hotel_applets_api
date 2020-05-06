package com.zhiliao.hotel.controller.myOrder;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.WxPayService;
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

import javax.servlet.http.HttpServletRequest;
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

    private final ZlOrderService orderService;
    private final ZlOrderDetailService orderDetailService;
    private WxPayService wxPayService;

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

    @ApiOperation(value = "提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Long", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "Long", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "path", name = "roomNumber", dataType = "String", required = true, value = "房间编号")
    })
    @PostMapping("submitOrder/{hotelID}/{hotelName}/{roomID}/{roomNumber}")
    @UserLoginToken
    @ResponseBody
    public ReturnString submitOrder(
            String token,
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
        Long userID = TokenUtil.getUserId(token);

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

    @ApiOperation(value = "支付回调")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "sign", dataType = "String", required = true, value = "签名"),
            @ApiImplicitParam(paramType = "path", name = "out_trade_no", dataType = "String", required = true, value = "商户订单号")

    })
    @PostMapping("wxPayReturn")
    @UserLoginToken
    @ResponseBody
    public ReturnString wxPayReturn(String token, @PathVariable("sign") String sign, @PathVariable("out_trade_no") String out_trade_no) {
        try {
            Map<String, Object> response = wxPayService.wxPayReturn(sign, out_trade_no);
            return new ReturnString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("支付回调失败");
        }
    }

}
