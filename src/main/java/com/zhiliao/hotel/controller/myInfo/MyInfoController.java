package com.zhiliao.hotel.controller.myInfo;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.myInfo.vo.MyInfoReturn;
import com.zhiliao.hotel.mapper.ZlCouponMapper;
import com.zhiliao.hotel.model.ZlWxuser;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Api(tags = "我的_林生")
@RestController
@RequestMapping("My")
public class MyInfoController {

    private static final Logger logger = LoggerFactory.getLogger(MyInfoController.class);

    @Autowired
    private ZlWxuserService zlWxuserService;

    @Autowired
    private ZlOrderService zlOrderService;

    @Autowired
    private ZlCommentService zlCommentService;

    @Autowired
    private ZlHotelService zlHotelService;

    @Autowired
    private ZlCouponMapper zlCouponMapper;

    @ApiOperation(value = "我的_界面信息_林生")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "int", required = true, value = "酒店ID")
    })
    @UserLoginToken
    @PostMapping("Info")
    public ReturnString findAllOrder(HttpServletRequest request, Integer hotelID) {

        try {

            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);

            ZlWxuser zlWxuser = zlWxuserService.findWxuserByUserId(userId);
            Long waitForPayTotal = zlOrderService.waitForPayTotal(userId);
            Long waitForGoodsTotal = zlOrderService.waitForGoodsTotal(userId);
            Long orderTotal = zlOrderService.allOrderTotal(userId);
            Integer couponCount = zlCouponMapper.getCouponCount(userId);
            Long appraiseTotal = zlCommentService.waitAppraiseTotal(userId);
            String receptionTel = zlHotelService.getReceptionTel(hotelID);

            MyInfoReturn myInfoReturn = new MyInfoReturn();
            myInfoReturn.setNickname(zlWxuser.getNickname()); //昵称
            myInfoReturn.setHeadimgurl(zlWxuser.getHeadimgurl()); //头像
            myInfoReturn.setWaitForPay(waitForPayTotal); //待支付订单数量
            myInfoReturn.setWaitForGoods(waitForGoodsTotal); //代配送订单数量
            myInfoReturn.setAllOrder(orderTotal); //订单总数量
            myInfoReturn.setCoupons(couponCount); //优惠总券数量
            myInfoReturn.setAppraise(appraiseTotal); //我的评价
            myInfoReturn.setServiceTel(receptionTel); //前台服务电话

            logger.error("我的界面：" + myInfoReturn);

            return new ReturnString(myInfoReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取信息失败");
        }

    }

}
