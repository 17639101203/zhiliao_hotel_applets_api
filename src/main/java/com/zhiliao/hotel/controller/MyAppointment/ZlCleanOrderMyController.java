package com.zhiliao.hotel.controller.MyAppointment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderMyService;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import com.zhiliao.hotel.utils.DateUtils;
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

import static com.zhiliao.hotel.utils.DateUtils.getDateByString;
import static com.zhiliao.hotel.utils.DateUtils.getTimeByDate;
import static com.zhiliao.hotel.utils.OrderIDUtil.CreateOrderID;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:49
 * @Description:
 */
@Api(tags = "清扫接口")
@RestController
@RequestMapping("clean")
public class ZlCleanOrderMyController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCleanOrderMyController.class);

    private ZlCleanOrderMyService zlCleanOrderMyService;

    @Autowired
    public ZlCleanOrderMyController(ZlCleanOrderService zlCleanOrderService) {
        this.zlCleanOrderMyService = zlCleanOrderMyService;
    }



    @ApiOperation(value = "清扫服务订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = true, value = "不传：查询全部，-1：取消，0：等待确认，1：已确认，2已处理"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("findByUserId")
    @ResponseBody
    @UserLoginToken
    public ReturnString findAllUserId(String token ,Integer orderstatus,Integer pageNo,Integer pageSize){
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult cleanorders = zlCleanOrderMyService.findAllByStatus(userId,orderstatus,pageNo,pageSize);;
            return new ReturnString(cleanorders);
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
    public ReturnString cleanOrderDetail(Long orderID){
        try {
            ZlCleanOrder cleanorder = zlCleanOrderMyService.orderDetail(orderID);
            return new ReturnString(cleanorder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

}
