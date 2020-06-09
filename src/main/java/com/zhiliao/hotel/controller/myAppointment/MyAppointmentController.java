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
import org.springframework.web.bind.annotation.*;

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
    private ZlHotelFacilityOrderService hotelFacilityOrderService;
    @Autowired
    public MyAppointmentController(MyAppointmentService myAppointmentService) {
        this.myAppointmentService = myAppointmentService;
    }

    @ApiOperation(value = "清扫服务订单展示_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "int", required = false, value = "不传：查询全部，-1：取消，0：待清扫，1：已完成"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @PostMapping("cleanFindAll")
    @ResponseBody
    //@UserLoginToken
    @PassToken
    public ReturnString cleanFindAll(HttpServletRequest request , Integer orderstatus, Integer pageNo, Integer pageSize){
        try {
            String token = request.getHeader("token");
            //Long userId = TokenUtil.getUserId(token);
            Long userId = (long)2;
            logger.info("用户id" + userId);
            if (userId == null){
                return new ReturnString("用户不存在");
            }
            PageInfoResult cleanorders = myAppointmentService.cleanFindAll(userId,orderstatus,pageNo,pageSize);
            return new ReturnString(cleanorders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }


    @ApiOperation(value = "发票订单展示_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "invoicestatus", dataType = "Integer", required = false, value = "不传：查询全部，-1：已取消，0：未开票，1：开票中，2：已开票"),
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
            return new ReturnString(invoices);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }



    @ApiOperation(value = "报修订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = false, value = "不传：查询全部，-1：已取消，0：待维修, 1: 已完成"),
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
            return new ReturnString(repairOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
    @ApiOperation(value = "客房服务订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderstatus", dataType = "Integer", required = false, value = "不传：查询全部，-1：取消，0：待配送, 1: 已完成"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @UserLoginToken
    @PostMapping("serviceFindAll")
    @ResponseBody
    public ReturnString serviceFindAll(HttpServletRequest request,Integer orderstatus,Integer pageNo,Integer pageSize){
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("用户id" + userId);
            PageInfoResult serviceOrders = myAppointmentService.serviceFindAll(userId,orderstatus,pageNo,pageSize);
            return new ReturnString(serviceOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }


    @ApiOperation(value = "设施预定订单展示", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：不传: 查询全部 ,-1.已取消；0.待确认；1.已确认"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("facilityOrderFindall")
    public ReturnString findAllOrder(HttpServletRequest request, Integer orderStatus,Integer pageNo, Integer pageSize) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = hotelFacilityOrderService.findAllOrder(userId, orderStatus, pageNo, pageSize);
            return new ReturnString(allOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "叫醒订单列表展示", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：不传: 查询全部 ,-1.已取消；0.待处理；1.已完成"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    //@PassToken
    @PostMapping("WakeOrderFindall")
    public ReturnString findAllWakeOrder(HttpServletRequest request, Integer orderStatus,Integer pageNo, Integer pageSize) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            //Long userId = (long)150;
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = myAppointmentService.findAllWakeOrder(userId, orderStatus, pageNo, pageSize);
            return new ReturnString(allOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "租车订单列表展示", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：不传: 查询全部 ,-1.已取消；0.待处理；1.已完成"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("rentCarOrderFindall")
    public ReturnString findAllRentCarOrder(HttpServletRequest request, Integer orderStatus,Integer pageNo, Integer pageSize) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = myAppointmentService.findAllRentCarOrder(userId, orderStatus, pageNo, pageSize);
            return new ReturnString(allOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "退房订单列表展示", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：不传: 查询全部 ,-1.取消退房；0.等待退房；1.退房完成"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("checkOutOrderFindall")
    public ReturnString findAllCheckOutOrder(HttpServletRequest request, Integer orderStatus,Integer pageNo, Integer pageSize) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = myAppointmentService.findAllCheckOutOrder(userId, orderStatus, pageNo, pageSize);
            return new ReturnString(allOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "续住订单列表展示", notes = "可传入不同的请求参数，查询各种类型的全部订单。例如：获取用户全部订单（不区分订单类型）列表的数据，传入token即可。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "int", required = false, value = "订单状态：不传: 查询全部 ,-1.已取消；0.待处理；1.已完成"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @UserLoginToken
    @PostMapping("continueLiveOrderFindall")
    public ReturnString findAllContinueLiveOrder(HttpServletRequest request, Integer orderStatus,Integer pageNo, Integer pageSize) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("我的订单，用户ID：" + userId);
            PageInfoResult allOrder = myAppointmentService.findAllContinueLiveOrder(userId, orderStatus, pageNo, pageSize);
            return new ReturnString(allOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "取消订单_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = "orderid",dataType = "long",required = true,value = "订单id"),
            @ApiImplicitParam(paramType = "path",name = "orderServiceType",dataType = "int",required = true,
                    value = "每个服务订单的类型标识 1: 取消清扫订单, 2: 取消发票订单, 3: 取消报修订单,4: 取消设施订单,5:取消客房服务订单," +
                            "6: 取消叫醒订单, 7: 取消租车订单, 8: 取消退房订单, 9: 取消续住订单")
    })
    @GetMapping("cancelOrder/{orderid}/{orderServiceType}")
    @UserLoginToken
    public ReturnString cancelOrder(@PathVariable Long orderid ,@PathVariable Integer orderServiceType){

        try {
            myAppointmentService.cancelOrder(orderid,orderServiceType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("取消失败");
        }
        return new ReturnString(0,"已取消");
    }


    @ApiOperation(value = "我的预约各订单类型的数量_徐向向")
    @GetMapping("myAppointementCount")
    @UserLoginToken
    //@PassToken
    public ReturnString myAppointementCount(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        //Long userId = (long)150;
        try {
            Map<String,Integer> resultCount = myAppointmentService.myAppointementCount(userId);
            return new ReturnString(resultCount);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

}
