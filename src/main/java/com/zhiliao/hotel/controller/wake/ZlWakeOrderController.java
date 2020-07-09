package com.zhiliao.hotel.controller.wake;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.wake.params.ZlWakeOrderParam;
import com.zhiliao.hotel.model.ZlWakeOrder;
import com.zhiliao.hotel.service.ZlWakeOrderService;
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
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-05 15:37
 **/
@Api(tags = "叫醒服务接口_首页_徐向向")
@RestController
@RequestMapping("wake")
public class ZlWakeOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ZlWakeOrderController.class);

    @Autowired
    private ZlWakeOrderService wakeOrderService;

    @UserLoginToken
    //@PassToken
    @ApiOperation(value = "提交叫醒订单")
    @PostMapping("addWakeOrder")
    public ReturnString addWakeOrder(HttpServletRequest request, @RequestBody ZlWakeOrderParam wakeOrderParm) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        //long userId = System.currentTimeMillis();
        ZlWakeOrder wakeOrder = new ZlWakeOrder();
        wakeOrder.setHotelid(wakeOrderParm.getHotelId());
        wakeOrder.setHotelname(wakeOrderParm.getHotelName());
        wakeOrder.setRoomid(wakeOrderParm.getRoomId());
        wakeOrder.setRoomnumber(wakeOrderParm.getRoomNumber());
        wakeOrder.setWakedate((int) (wakeOrderParm.getWakeDate() / 1000));
        wakeOrder.setRemark(wakeOrderParm.getRemark());

        try {
            Map<String, Object> map = wakeOrderService.addWakeOrder(userId, wakeOrder);
            return new ReturnString(0, "提交成功,祝您好梦!", map);

        } catch (Exception e) {
            return new ReturnString("提交失败");
        }
    }

    @ApiOperation(value = "叫醒订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderID", value = "订单ID", required = true)
    })
    @GetMapping("wakeOrderDetail/{orderID}")
    @UserLoginToken
    @PassToken
    //@ResponseBody
    public ReturnString wakeOrderDetail(@PathVariable Long orderID) {

        try {
            ZlWakeOrder wakeOrder = wakeOrderService.wakeOrderDetail(orderID);
            return new ReturnString(wakeOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "取消叫醒订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderID", value = "订单ID", required = true)
    })
    @GetMapping("cancelWakeOrder/{orderID}")
    @UserLoginToken
    @PassToken
    public ReturnString cancelWakeOrder(@PathVariable Long orderID) {

        try {
            wakeOrderService.cancelWakeOrder(orderID);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户删除叫醒订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderID", value = "订单ID", required = true)
    })
    @GetMapping("dlWakeOrder/{orderID}")
    @UserLoginToken
    @PassToken
    public ReturnString dlWakeOrder(@PathVariable Long orderID) {

        try {
            wakeOrderService.dlWakeOrder(orderID);
            return new ReturnString(0, "删除成功");
        } catch (Exception e) {
            return new ReturnString(e.getMessage());
        }
    }
}
