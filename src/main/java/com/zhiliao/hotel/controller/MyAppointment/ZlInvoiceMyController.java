package com.zhiliao.hotel.controller.MyAppointment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceMyService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "开票模块接口")
@RestController
@RequestMapping("invoice")
public class ZlInvoiceMyController {

    @Autowired
    private ZlInvoiceMyService service;

    private static final Logger logger = LoggerFactory.getLogger(ZlInvoiceMyController.class);


    @ApiOperation(value = "发票订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "invoicestatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：已取消，0：未开票，1：开票中，2：已开票"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("findByUserId")
    @UserLoginToken
    @ResponseBody
    public ReturnString findAllByUserId(String token, Integer invoicestatus, Integer pageNo, Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult invoices = service.findAllByUserId(userId,invoicestatus,pageNo,pageSize);
            return new ReturnString(invoices);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="订单详情")
    @PostMapping("detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="Integer", name="invoiceid", value="发票订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString repairOrderDetail(Integer invoiceid){
        try {
            ZlInvoice invoice = service.orderDetail(invoiceid);
            return new ReturnString(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
