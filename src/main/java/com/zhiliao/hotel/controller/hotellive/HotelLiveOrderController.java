package com.zhiliao.hotel.controller.hotellive;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.hotellive.param.ZlCheckoutOrderParam;
import com.zhiliao.hotel.controller.hotellive.param.ZlContinueLiveOrderParam;
import com.zhiliao.hotel.controller.myOrder.ZlOrderController;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.model.ZlCheckoutOrder;
import com.zhiliao.hotel.model.ZlContinueLiveOrder;
import com.zhiliao.hotel.service.HotelLiveOrderService;
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
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:04
 **/

@Api(tags = "退房&续住订单接口_姬慧慧")
@RestController
@RequestMapping("hotelLive")
public class HotelLiveOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderController.class);

    @Autowired
    private HotelLiveOrderService hotelLiveOrderService;

    @ApiOperation(value = "退房订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Long", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "Long", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "path", name = "roomNumber", dataType = "String", required = true, value = "房间编号")
    })
    @PostMapping("checkoutOrder/{hotelID}/{hotelName}/{roomID}/{roomNumber}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString checkoutOrder(
            HttpServletRequest httpServletRequest,
            @PathVariable("hotelID") Integer hotelID,
            @PathVariable("hotelName") String hotelName,
            @PathVariable("roomID") Integer roomID,
            @PathVariable("roomNumber") String roomNumber,
            @RequestBody ZlCheckoutOrderParam zlCheckoutOrderParam) {

        //封装对象
        HotelBasicVO hotelBasicVO = new HotelBasicVO();
        hotelBasicVO.setHotelID(hotelID);
        hotelBasicVO.setHotelName(hotelName);
        hotelBasicVO.setRoomID(roomID);
        hotelBasicVO.setRoomNumber(roomNumber);
        //获取用户id
        String token = httpServletRequest.getHeader("token");
        Long userID = TokenUtil.getUserId(token);
//        Long userID = 150L;
        try {
            Map<String, Object> map = hotelLiveOrderService.checkoutOrder(userID, hotelBasicVO, zlCheckoutOrderParam);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("退房失败!");
        }
    }

    @ApiOperation(value = "取消退房订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("cancelCheckoutOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString cancelCheckoutOrder(@PathVariable("orderID") Long orderID) {
        try {
            hotelLiveOrderService.cancelCheckoutOrder(orderID);
            return new ReturnString(0, "取消退房成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消退房失败!");
        }
    }

    @ApiOperation(value = "退房订单详情_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("checkoutOrderDetail/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString checkoutOrderDetail(@PathVariable("orderID") Long orderID) {
        try {
            ZlCheckoutOrder checkoutOrder = hotelLiveOrderService.checkoutOrderDetail(orderID);
            return new ReturnString(checkoutOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败!");
        }
    }


    @ApiOperation(value = "用户删除退房订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("userDeleteCheckoutOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteCheckoutOrder(@PathVariable("orderID") Long orderID) {
        try {
            hotelLiveOrderService.userDeleteCheckoutOrder(orderID);
            return new ReturnString("用户删除退房成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("用户删除退房失败!");
        }
    }

    @ApiOperation(value = "续住订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Long", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "hotelName", dataType = "String", required = true, value = "酒店名"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "Long", required = true, value = "房间ID"),
            @ApiImplicitParam(paramType = "path", name = "roomNumber", dataType = "String", required = true, value = "房间编号")
    })
    @PostMapping("continueLiveOrder/{hotelID}/{hotelName}/{roomID}/{roomNumber}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString continueLiveOrder(
            HttpServletRequest httpServletRequest,
            @PathVariable("hotelID") Integer hotelID,
            @PathVariable("hotelName") String hotelName,
            @PathVariable("roomID") Integer roomID,
            @PathVariable("roomNumber") String roomNumber,
            @RequestBody ZlContinueLiveOrderParam zlContinueLiveOrderParam) {

        //封装对象
        HotelBasicVO hotelBasicVO = new HotelBasicVO();
        hotelBasicVO.setHotelID(hotelID);
        hotelBasicVO.setHotelName(hotelName);
        hotelBasicVO.setRoomID(roomID);
        hotelBasicVO.setRoomNumber(roomNumber);
        //获取用户id
        String token = httpServletRequest.getHeader("token");
        Long userID = TokenUtil.getUserId(token);
//        Long userID = 9L;
        try {
            Map<String, Object> map = hotelLiveOrderService.continueLiveOrder(userID, hotelBasicVO, zlContinueLiveOrderParam);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("续住失败!");
        }
    }

    @ApiOperation(value = "取消续住订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "续住订单ID")
    })
    @PostMapping("cancelContinueLiveOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString cancelContinueLiveOrder(@PathVariable("orderID") Long orderID) {
        try {
            hotelLiveOrderService.cancelContinueLiveOrder(orderID);
            return new ReturnString(0, "取消续住成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消续住失败!");
        }
    }

    @ApiOperation(value = "用户删除续住订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("userDeleteContinueLiveOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteContinueLiveOrder(@PathVariable("orderID") Long orderID) {
        try {
            hotelLiveOrderService.userDeleteContinueLiveOrder(orderID);
            return new ReturnString(0, "用户删除退房成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("用户删除退房失败!");
        }
    }

    @ApiOperation(value = "续住订单详情_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("continueLiveOrderDetail/{orderID}")
    @UserLoginToken
    //@PassToken
    @ResponseBody
    public ReturnString continueLiveOrderDetail(@PathVariable("orderID") Long orderID) {
        try {
            ZlContinueLiveOrder continueLiveOrder = hotelLiveOrderService.continueLiveOrderDetail(orderID);
            return new ReturnString(continueLiveOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败!");
        }
    }
}
