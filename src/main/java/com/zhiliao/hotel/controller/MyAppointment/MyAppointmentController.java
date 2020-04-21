package com.zhiliao.hotel.controller.MyAppointment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.zlInvoice;
import com.zhiliao.hotel.model.zlRepairorder;
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

@Api(tags = "我的预约服务接口")
@RestController
@RequestMapping("myAppointment")
public class MyAppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(MyAppointmentController.class);


    @Autowired
    private MyAppointmentService myAppointmentService;

    @ApiOperation(value = "清扫服务订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("cleanFindAll")
    @ResponseBody
    @UserLoginToken
    public ReturnString cleanFindAll(String token , Integer orderstatus, Integer pageNo, Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult cleanorders = myAppointmentService.cleanFindAll(userId,orderstatus,pageNo,pageSize);;
            return new ReturnString(cleanorders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="清扫订单详情")
    @PostMapping("cleanDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="long", name="orderID", value="清扫服务订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString cleanOrderDetail(Long orderID){
        try {
            ZlCleanOrder cleanorder = myAppointmentService.cleanOrderDetail(orderID);
            return new ReturnString(cleanorder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "发票订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "invoicestatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：已取消，0：未开票，1：开票中，2：已开票"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("invoiceFindAll")
    @UserLoginToken
    @ResponseBody
    public ReturnString invoiceFindAll(String token, Integer invoicestatus, Integer pageNo, Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult invoices = myAppointmentService.invoiceFindAll(userId,invoicestatus,pageNo,pageSize);
            return new ReturnString(invoices);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="发票订单详情")
    @PostMapping("invoiceDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="Integer", name="invoiceid", value="发票订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString invoiceOrderDetail(Integer invoiceid){
        try {
            zlInvoice invoice = myAppointmentService.invoiceOrderDetail(invoiceid);
            return new ReturnString(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "报修订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @UserLoginToken
    @PostMapping("repairFindAll")
    @ResponseBody
    public ReturnString repairFindAll(String token,Integer orderstatus,Integer pageNo,Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult repairOrders = myAppointmentService.repairFindAll(userId,orderstatus,pageNo,pageSize);
            return new ReturnString(repairOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="订单详情")
    @PostMapping("repairDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="long", name="orderID", value="清扫服务订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString repairOrderDetail(Long orderID){
        try {
            zlRepairorder repairOrder = myAppointmentService.repairOrderDetail(orderID);
            return new ReturnString(repairOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
