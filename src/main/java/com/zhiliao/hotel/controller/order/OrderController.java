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

    private ZlOrderService orderService;
    private ZlOrderDetailService orderDetailService;
    
    @Autowired
    public OrderController(ZlOrderService orderService,ZlOrderDetailService orderDetailService){
        this.orderService=orderService;
        this.orderDetailService=orderDetailService;
    }
    
    @ApiOperation(value="我的全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="token", dataType="String", required=true, value="token"),
            @ApiImplicitParam(paramType="query", name="payStatus", dataType="int", required=false, value="获取不同的订单：0无须支付;1:待支付;2已支付;3已取消;不传获取全部订单"),
    })
    @PassToken
    @PostMapping("all")
    public ReturnString findAllOrder(String token,Integer payStatus){
        
        try{
            
            Long userId=TokenUtil.getUserId(token);
            logger.info("我的全部订单id："+userId);
            if(userId==null){
                return new ReturnString("用户不存在");
            }
            List<ZlOrder> orderList=null;
            if(payStatus==null){
                orderList=orderService.findAllOrder(userId);
                return new ReturnString(orderList);
            }
            orderList=orderService.findOrderByPayStatus(userId,payStatus);
            return new ReturnString(orderList);
            
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
