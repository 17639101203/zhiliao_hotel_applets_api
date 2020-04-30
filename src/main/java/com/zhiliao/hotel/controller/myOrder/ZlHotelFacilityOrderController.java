package com.zhiliao.hotel.controller.myOrder;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "酒店设施订单接口")
@RestController
@RequestMapping("facilityOrder")
public class ZlHotelFacilityOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlHotelFacilityOrderController.class);

    @Autowired
    private ZlHotelFacilityOrderService hotelFacilityOrderService;


    @ApiOperation(value = "我的订单", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：-1.已取消；0.待确认；1.已确认"),
            @ApiImplicitParam(paramType = "query", name = "payStatus", dataType = "int", required = false, value = "支付状态：0：无须支付；1：待支付；2：已支付。"),
            @ApiImplicitParam(paramType = "query", name = "payType", dataType = "int", required = false, value = "支付方式：0.无支付方式；1.微信；2.支付宝；3.银行卡；4.其它。"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("all")
    public ReturnString findAllOrder(String token, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize) {

        try {

            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = hotelFacilityOrderService.findAllOrder(userId, orderStatus, payStatus, payType, pageNo, pageSize);
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


        try {
            ZlHotelFacilityOrder zlHotelFacilityOrder = hotelFacilityOrderService.findOrder( orderID);
            return new ReturnString(zlHotelFacilityOrder);
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
    @PostMapping("orderById/{orderID}")
    @UserLoginToken
    @ResponseBody
    public ReturnString orderById(String token, @PathVariable Long orderID) {
        try {
            hotelFacilityOrderService.byOrderId(orderID);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消失败");
        }
    }

}
