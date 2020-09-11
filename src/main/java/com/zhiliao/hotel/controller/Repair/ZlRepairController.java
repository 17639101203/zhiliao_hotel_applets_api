package com.zhiliao.hotel.controller.Repair;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderVO;
import com.zhiliao.hotel.controller.file.UploadFileController;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlRepairorderDTO;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "首页_报修模块接口_章英杰")
@RestController
@RequestMapping("repair")
@Slf4j
public class ZlRepairController {

    @Autowired
    private ZlRepairService service;

    @Autowired
    private UploadFileController fileController;


    @ApiOperation(value = "添加报修信息")
    @PostMapping(value = "addrepair")
    @UserLoginToken
    public ReturnString addrepair(@Validated @RequestBody RepairParam repairParam, HttpServletRequest request) {

        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        try {
            Map<String, Object> map = service.addRepairMsg(userid, repairParam);
            return new ReturnString(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }


    @ApiOperation(value = "查询报修订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "报修订单ID")
    })
    @GetMapping("findRepairOrder/{orderID}")
    @UserLoginToken
    public ReturnString<Map<String, Object>> findRepairOrder(@PathVariable("orderID") Long orderID) {
        try {
            ZlRepairorderDTO zlRepairorderDTO = service.findRepairOrder(orderID);
            return new ReturnString(zlRepairorderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询报修订单详情出错!");
        }
    }


    @ApiOperation(value = "取消报修预约")
    @PostMapping("cancelRepairOrder/{serialnumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "报修订单ID")
    })
    @UserLoginToken
    public ReturnString cancelRepairOrder(@PathVariable("orderID") Long orderID) {

        try {
            service.cancelRepairOrder(orderID);
            return new ReturnString<>(0, "预约已取消");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户删除报修订单_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderID", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("userDeleteRepairOrder/{orderID}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteRepairOrder(@PathVariable("orderID") Long orderID) {
        try {
            service.userDeleteRepairOrder(orderID);
            return new ReturnString("用户删除报修成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("用户删除报修失败!");
        }
    }

}
