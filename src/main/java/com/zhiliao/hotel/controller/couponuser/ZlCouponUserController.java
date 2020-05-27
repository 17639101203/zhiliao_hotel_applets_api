package com.zhiliao.hotel.controller.couponuser;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.couponuser.result.ZlCouponUserResult;
import com.zhiliao.hotel.model.ZlCouponUser;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "我的用户优惠卷接口_我的_徐向向")
@RequestMapping("couponUser")
@RestController
public class ZlCouponUserController {
    private static final Logger logger = LoggerFactory.getLogger(ZlCouponUserController.class);

    @Autowired
    private ZlCouponUserService couponUserService;

    @ApiOperation(value="我的优惠卷订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数"),
    })
    //@UserLoginToken
    @PassToken
    @PostMapping("couponUserAll")
    public ReturnString listCouponUsers(HttpServletRequest request, Integer pageNo, Integer pageSize){

        try{
            String token = request.getHeader("token");
            //Long userId= TokenUtil.getUserId(token);
            Long userId = (long)10;
            logger.info("我的优惠卷，用户ID："+userId);
            List<ZlCouponUserResult> result = couponUserService.listCouponUser(userId,pageNo,pageSize);
            return new ReturnString(result);

        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }

    }
    @ApiOperation(value="获取有效优惠卷的数量, 可用优惠卷数量")
    @ApiImplicitParams({

    })
    //@UserLoginToken
    @PassToken
    @PostMapping("count")
    public ReturnString count(HttpServletRequest request){

        try{
            String token = request.getHeader("token");
            //Long userId= TokenUtil.getUserId(token);
            Long userId = (long)10;
            logger.info("我的优惠卷，用户ID："+userId);
            Integer count = couponUserService.count(userId);
            return new ReturnString(count);
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
    @ApiOperation(value="获取有效优惠卷, 可用优惠卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数"),
    })
    @UserLoginToken
    @PostMapping("effective")
    public ReturnString effective(HttpServletRequest request,Integer pageNo, Integer pageSize){

        try{
            String token = request.getHeader("token");
            Long userId= TokenUtil.getUserId(token);
            logger.info("我的优惠卷，用户ID："+userId);
            PageInfoResult result = couponUserService.effective(userId,pageNo,pageSize);
            return new ReturnString(result);

        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

}
