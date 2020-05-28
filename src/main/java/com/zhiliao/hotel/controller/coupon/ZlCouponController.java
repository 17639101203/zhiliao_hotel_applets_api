package com.zhiliao.hotel.controller.coupon;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlCoupon;
import com.zhiliao.hotel.service.ZlCouponService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-27 11:18
 **/
@Api(tags = "平台发放优惠卷展示接口_首页_徐向向")
@RequestMapping("coupon")
@RestController
public class ZlCouponController {
    private static final Logger logger = LoggerFactory.getLogger(ZlCouponUserController.class);

    @Autowired
    private ZlCouponService couponService;

    @ApiOperation(value = "获取平台发放的优惠卷")
    //@UserLoginToken
    @PassToken
    @GetMapping("couponFindAll")
    public ReturnString couponFindAll(HttpServletRequest request){

        try {
            String token = request.getHeader("token");
            //Long userId = TokenUtil.getUserId(token);
            Long userId = (long)1;
            List<ZlCoupon> coupons = couponService.couponFindAll(userId);
            return new ReturnString(coupons);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取有误");
        }

    }
}
