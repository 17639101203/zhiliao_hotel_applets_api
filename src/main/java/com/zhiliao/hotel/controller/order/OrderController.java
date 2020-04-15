package com.zhiliao.hotel.controller.order;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import com.zhiliao.hotel.service.ZlOrderService;
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

import java.util.List;

/**
 *
 */
@Api(tags="订单接口")
@RestController
@RequestMapping("order")
public class OrderController{
    
    private static final Logger logger=LoggerFactory.getLogger(OrderController.class);
    
    private TokenUtil tokenUtil;
    private ZlOrderService orderService;
    private ZlOrderDetailService orderDetailService;
    
    @Autowired
    public OrderController(TokenUtil tokenUtil,ZlOrderService orderService,ZlOrderDetailService orderDetailService){
        this.tokenUtil=tokenUtil;
        this.orderService=orderService;
        this.orderDetailService=orderDetailService;
    }
    
    @ApiOperation(value="我的全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="token", dataType="String", required=true, value="token"),
    })
    @PassToken
    @PostMapping("all")
    public ReturnString findAllOrder(String token){
        try{
            Long userId=tokenUtil.getUserId(token);
            logger.info("我的全部订单id："+userId);
            List<ZlOrder> allOrderList=orderService.findAllOrder(userId);
            return new ReturnString(allOrderList);
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
    
    @ApiOperation(value="订单详情")
    @PostMapping("Detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType="Long", name="orderID", value="订单ID", required=true)
    })
    @PassToken
    @ResponseBody
    public ReturnString findOrderDetail(Long orderID){
        logger.info("订单ID："+orderID);
        try{
            ZlOrderDetail orderDetail=orderDetailService.findOrder(orderID);
            return new ReturnString(orderDetail);
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }
    
}
