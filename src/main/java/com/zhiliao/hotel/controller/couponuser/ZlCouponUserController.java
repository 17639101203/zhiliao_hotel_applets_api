package com.zhiliao.hotel.controller.couponuser;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlCouponUserService;
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

@Api(tags = "用户优惠卷接口")
@RequestMapping("couponUser")
@RestController
public class ZlCouponUserController {
    private static final Logger logger = LoggerFactory.getLogger(ZlCouponUserController.class);

    @Autowired
    private ZlCouponUserService couponUserService;

    @ApiOperation(value="我的优惠卷订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="token", dataType="String", required=true, value="token"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数"),
    })
    @UserLoginToken
    @PostMapping("couponUserAll")
    public ReturnString listCouponUsers(String token, Integer pageNo, Integer pageSize){

        try{
            Long userId= TokenUtil.getUserId(token);
            logger.info("我的优惠卷，用户ID："+userId);
            PageInfoResult result = couponUserService.listCouponUser(userId,pageNo,pageSize);
            return new ReturnString(result);

        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }

    }

}
