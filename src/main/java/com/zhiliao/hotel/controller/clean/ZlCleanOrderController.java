package com.zhiliao.hotel.controller.clean;

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
    public ReturnString<Map<String, Object>> addCleanOrder(HttpServletRequest request, @RequestBody CleanParm cleanParm) {

        Map<String, Object> map = new HashMap<>();
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        if (cleanParm.getBookdate() < nowTime || cleanParm.getBookdate() - nowTime > 60 * 60 * 48) {
            return new ReturnString<>(-1, "预定清扫时间不在规定范围内");
        }
        String serialnumber = OrderIDUtil.createOrderID("");
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setUserid(userid);   //用户ID  根据token获取userId
        zlCleanOrder.setSerialnumber(serialnumber);   //订单编号
        zlCleanOrder.setHotelid(cleanParm.getHotelid());   //酒店ID
        zlCleanOrder.setHotelname(cleanParm.getHotelname());   //酒店名
        zlCleanOrder.setRoomid(cleanParm.getRoomid());   //房间ID
        zlCleanOrder.setRoomnumber(cleanParm.getRoomnumber());   //房间号
        zlCleanOrder.setComeformid(1);   //来自1小程序C端，2小程序B端，3公众号, 4民宿，5好评返现，6分时酒店
        zlCleanOrder.setBookdate(cleanParm.getBookdate());   //预定时间
        zlCleanOrder.setRemark(cleanParm.getRemark());   //其他需求备注
        zlCleanOrder.setCreatedate(nowTime);   //下单时间
        zlCleanOrder.setUpdatedate(nowTime);   //支付/取消时间
        zlCleanOrderService.addCleanOrder(zlCleanOrder);
        map.put("serialnumber", serialnumber);
        return new ReturnString<>(0, "下单成功", map);
    }


    @ApiOperation(value = "查询清扫订单详情")
    @PostMapping("selectCleanDetails/{serialnumber}")
    @UserLoginToken
    public ReturnString<Map<String, Object>> selectCleanDetails(HttpServletRequest request, @PathVariable String serialnumber) {

        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        Map<String, Object> cleanmap = zlCleanOrderService.selectCleanDetails(userid, serialnumber);
        return new ReturnString<>(cleanmap);
    }

}
