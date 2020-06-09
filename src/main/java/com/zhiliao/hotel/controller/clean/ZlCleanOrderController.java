package com.zhiliao.hotel.controller.clean;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.clean.cleanparm.CleanParm;
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

        Map<String, Object> map = new HashMap<>();
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        if (cleanParm.getBookdate() < nowTime || cleanParm.getBookdate() - nowTime > 60 * 60 * 48) {
            return new ReturnString<>(-1, "预定清扫时间不在规定范围内");
        }

        Long userid = TokenUtil.getUserId(request.getHeader("token"));
//        Long userid = 77L;

        try {
            zlCleanOrderService.addCleanOrder(userid, cleanParm);
            return new ReturnString<>(0, "下单成功!", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>(0, "下单失败!", map);
        }

    }


    @ApiOperation(value = "查询清扫订单详情")
    @GetMapping("selectCleanDetails/{serialnumber}")
    @UserLoginToken
//    @PassToken
    public ReturnString<Map<String, Object>> selectCleanDetails(@PathVariable("serialnumber") String serialnumber) {

        Map<String, Object> cleanmap = null;
        try {
            cleanmap = zlCleanOrderService.selectCleanDetails(serialnumber);
            return new ReturnString<>(cleanmap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("查询失败!");
        }
    }


    @ApiOperation(value = "取消清扫预约")
    @PostMapping("cancelCleanOrder/{serialnumber}")
    @UserLoginToken
//    @PassToken
    public ReturnString cancelCleanOrder(@PathVariable("serialnumber") String serialnumber) {
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        zlCleanOrderService.removeCleanOrder(serialnumber, nowTime);
        return new ReturnString<>(0, "预约已取消");
    }


}
