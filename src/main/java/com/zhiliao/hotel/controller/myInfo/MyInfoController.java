package com.zhiliao.hotel.controller.myInfo;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.myInfo.vo.MyInfoReturn;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Api(tags="我的_林生")
@RestController
@RequestMapping("My")
public class MyInfoController{
    
    private static final Logger logger=LoggerFactory.getLogger(MyInfoController.class);
    
    @Autowired
    private ZlWxuserService zlWxuserService;
    
    @Autowired
    private ZlOrderService zlOrderService;
    
    @Autowired
    private ZlCouponUserService couponUserService;
    
    @Autowired
    private ZlCommentService zlCommentService;
    
    @Autowired
    private ZlHotelService zlHotelService;
    
    // TODO: 2020/5/25  @PassToken
    @ApiOperation(value="我的_界面信息_林生")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="hotelID", dataType="int", required=true, value="酒店ID")
    })
    // @UserLoginToken
    @PassToken
    @PostMapping("Info")
    public ReturnString findAllOrder(HttpServletRequest request,Integer hotelID){
        
        try{
            
            // TODO: 2020/5/23  userId
            // String token=request.getHeader("token");
            // Long userId=TokenUtil.getUserId(token);
            
            Long userId=2L;
            
            ZlWxuser zlWxuser=zlWxuserService.findWxuserByUserId(userId);
            Long waitForPayTotal=zlOrderService.waitForPayTotal(userId);
            Long waitForGoodsTotal=zlOrderService.waitForGoodsTotal(userId);
            Long orderTotal=zlOrderService.allOrderTotal(userId);
            Integer couponCount=couponUserService.count(userId);
            Long appraiseTotal=zlCommentService.waitAppraiseTotal(userId);
            String receptionTel=zlHotelService.getReceptionTel(hotelID);
            
            MyInfoReturn myInfoReturn=new MyInfoReturn();
            myInfoReturn.setNickname(zlWxuser.getNickname());
            myInfoReturn.setHeadimgurl(zlWxuser.getHeadimgurl());
            myInfoReturn.setWaitForPay(waitForPayTotal);
            myInfoReturn.setWaitForGoods(waitForGoodsTotal);
            myInfoReturn.setAllOrder(orderTotal);
            myInfoReturn.setCoupons(couponCount);
            myInfoReturn.setAppraise(appraiseTotal);
            myInfoReturn.setServiceTel(receptionTel);
            
            logger.error("我的界面："+myInfoReturn);
            
            return new ReturnString(myInfoReturn);
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取信息失败");
        }
        
    }
    
}
