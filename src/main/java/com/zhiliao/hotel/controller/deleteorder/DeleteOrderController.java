package com.zhiliao.hotel.controller.deleteorder;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.DeleteOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-28 15:39
 **/
@Api(tags = "用户删除订单接口_姬慧慧")
@RestController
@RequestMapping("userDeleteOrder")
public class DeleteOrderController {

    @Autowired
    private DeleteOrderService deleteOrderService;

    @ApiOperation(value = "删除订单_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderid", dataType = "Long", required = true, value = "订单id"),
            @ApiImplicitParam(paramType = "path", name = "orderServiceType", dataType = "int", required = true,
                    value = "每个服务订单的类型标识 1: 删除清扫订单, 2: 删除发票订单, 3: 删除报修订单,4: 删除设施订单,5:删除客房服务订单," +
                            "6: 删除叫醒订单, 7: 删除租车订单, 8: 删除退房订单, 9: 删除续住订单")
    })
    @GetMapping("deleteOrder/{orderid}/{orderServiceType}")
    @UserLoginToken
    public ReturnString deleteOrder(@PathVariable Long orderid, @PathVariable Integer orderServiceType) {

        try {
            deleteOrderService.deleteOrder(orderid, orderServiceType);
            return new ReturnString(0, "删除订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("删除订单失败!");
        }
    }

}
