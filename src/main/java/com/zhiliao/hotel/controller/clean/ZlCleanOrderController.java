package com.zhiliao.hotel.controller.clean;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.clean.cleanparm.CleanParm;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlCleanOrderDTO;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "首页_清扫接口_章英杰")
@RestController
@RequestMapping("clean")
@Slf4j
public class ZlCleanOrderController {


    @Autowired
    private ZlCleanOrderService zlCleanOrderService;


    @ApiOperation(value = "清扫下单")
    @PostMapping("addCleanOrder")
    @UserLoginToken
//    @PassToken
    public ReturnString<Map<String, Object>> addCleanOrder(HttpServletRequest request, @RequestBody CleanParm cleanParm) {

        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        if (cleanParm.getBookdate() / 1000 < nowTime || cleanParm.getBookdate() / 1000 - nowTime > 60 * 60 * 48) {
            return new ReturnString<>(-1, "预定清扫时间不在规定范围内");
        }

        Long userid = TokenUtil.getUserId(request.getHeader("token"));
//        Long userid = 77L;

        try {
            Map<String, Object> map = zlCleanOrderService.addCleanOrder(userid, cleanParm);
            return new ReturnString<>(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString<>(e.getMessage());
        }

    }


    @ApiOperation(value = "查询清扫订单详情")
    @GetMapping("selectCleanDetails/{orderID}")
    @UserLoginToken
//    @PassToken
    public ReturnString selectCleanDetails(@PathVariable("orderID") Long orderID) {

        try {
            ZlCleanOrderDTO zlCleanOrderDTO = zlCleanOrderService.selectCleanDetails(orderID);
            return new ReturnString<>(zlCleanOrderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("查询失败!");
        }
    }


    @ApiOperation(value = "取消清扫预约")
    @PostMapping("cancelCleanOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    public ReturnString cancelCleanOrder(@PathVariable("orderID") Long orderID) {
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        try {
            zlCleanOrderService.removeCleanOrder(orderID);
            return new ReturnString<>(0, "预约已取消");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "删除清扫订单")
    @PostMapping("deleteCleanOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    public ReturnString deleteCleanOrder(@PathVariable("orderID") Long orderID) {

        try {
            zlCleanOrderService.deleteCleanOrder(orderID);
            return new ReturnString<>(0, "订单已删除!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("删除清扫订单失败!");
        }
    }


}
