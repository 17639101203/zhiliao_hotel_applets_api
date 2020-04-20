package com.zhiliao.hotel.controller.MyAppointment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.file.UploadFileController;
import com.zhiliao.hotel.model.zlRepair;
import com.zhiliao.hotel.model.zlRepairorder;
import com.zhiliao.hotel.service.ZlRepairMyService;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "报修模块接口")
@RestController
@RequestMapping("repair")
public class ZlRepairMyController {

    @Autowired
    private ZlRepairMyService service;

    @Autowired
    private UploadFileController fileController;

    private static final Logger logger = LoggerFactory.getLogger(ZlRepairMyController.class);



    @ApiOperation(value = "报修订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("findByUserId")
    @ResponseBody
    public ReturnString findAllByUserId(String token,Integer orderstatus,Integer pageNo,Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult repairOrders = service.findAllByUserId(userId,orderstatus,pageNo,pageSize);
            return new ReturnString(repairOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value="订单详情")
    @PostMapping("detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="long", name="orderID", value="清扫服务订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString repairOrderDetail(Long orderID){
        try {
            zlRepairorder repairOrder = service.orderDetail(orderID);
            return new ReturnString(repairOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

}
