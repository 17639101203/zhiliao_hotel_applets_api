package com.zhiliao.hotel.controller.myAppointment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.*;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "我的预约服务接口_我的_徐向向")
@RestController
@RequestMapping("myAppointment")
public class MyAppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(MyAppointmentController.class);

    private final MyAppointmentService myAppointmentService;

    @Autowired
    public MyAppointmentController(MyAppointmentService myAppointmentService) {
        this.myAppointmentService = myAppointmentService;
    }

    @ApiOperation(value = "清扫服务订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("cleanFindAll")
    @ResponseBody
    @UserLoginToken
    public ReturnString cleanFindAll(HttpServletRequest request , Integer orderstatus, Integer pageNo, Integer pageSize){
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult cleanorders = myAppointmentService.cleanFindAll(userId,orderstatus,pageNo,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("repairOrders",cleanorders);
            map.put("orderServiceType","清扫服务");
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }


    @ApiOperation(value = "发票订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "invoicestatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：已取消，0：未开票，1：开票中，2：已开票"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("invoiceFindAll")
    @UserLoginToken
    @ResponseBody
    public ReturnString invoiceFindAll(HttpServletRequest request, Integer invoicestatus, Integer pageNo, Integer pageSize){
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            PageInfoResult invoices = myAppointmentService.invoiceFindAll(userId,invoicestatus,pageNo,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("repairOrders",invoices);
            map.put("orderServiceType","发票服务");
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "报修订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @UserLoginToken
    @PostMapping("repairFindAll")
    @ResponseBody
    public ReturnString repairFindAll(HttpServletRequest request,Integer orderstatus,Integer pageNo,Integer pageSize){
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            PageInfoResult repairOrders = myAppointmentService.repairFindAll(userId,orderstatus,pageNo,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("repairOrders",repairOrders);
            map.put("orderServiceType","报修服务");
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
