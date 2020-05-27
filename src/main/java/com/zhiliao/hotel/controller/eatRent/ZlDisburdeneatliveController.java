package com.zhiliao.hotel.controller.eatRent;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.myOrder.vo.OrderInfoVO;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import com.zhiliao.hotel.service.MyAppointmentService;
import com.zhiliao.hotel.service.ZlDisburdeneatliveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Api(tags="安心食住接口_林生")
@RestController
@RequestMapping("eatRent")
public class ZlDisburdeneatliveController{
    
    @Autowired
    private ZlDisburdeneatliveService disburdeneatliveService;
    
    // TODO: 2020/5/25  @PassToken
    @ApiOperation(value="安心食住_林生")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="hotelID", dataType="int", required=true, value="酒店ID")
    })
    // @UserLoginToken
    @PassToken
    @PostMapping("eatRentInfo")
    public ReturnString findAllOrder(HttpServletRequest request,Integer hotelID){
        try{
            // TODO: 2020/5/23  userId
            // String token=request.getHeader("token");
            // Long userId=TokenUtil.getUserId(token);
            // logger.info("我的订单，用户ID："+userId);
            Long userId=1L;
            long ID=userId;
            ZlDisburdeneatlive zlDisburdeneatlive=disburdeneatliveService.find(((int)ID),hotelID);
            return new ReturnString(zlDisburdeneatlive);
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
    
}
