package com.zhiliao.hotel.controller;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.SjWeixinuser;
import com.zhiliao.hotel.service.SjWeixinuserService;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiegege on 2019/10/14.
 */

@Api(tags = "微信用户接口")
@RestController
@RequestMapping("user")
public class SjWeixinuserController {

    private static final Logger logger = LoggerFactory.getLogger(SjWeixinuserController.class);

    private SjWeixinuserService sjWeixinuserService;
    private TokenUtil tokenUtil;
    private RedisCommonUtil redisCommonUtil;

    @Autowired
    public SjWeixinuserController(SjWeixinuserService sjWeixinuserService, TokenUtil tokenUtil, RedisCommonUtil redisCommonUtil) {
        this.sjWeixinuserService = sjWeixinuserService;
        this.tokenUtil = tokenUtil;
        this.redisCommonUtil = redisCommonUtil;
    }

    @ApiOperation(value = "微信用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "weixinOpenid", dataType = "String", required = true, value = "微信openid")
    })
    @PassToken
    @PostMapping("weixinUserLogin")
    public ReturnString weixinUserLogin(String weixinOpenid) {
        try {
            logger.info("开始登录->参数->微信openid->weixinOpenid：" + weixinOpenid);
            SjWeixinuser sjWeixinuser = sjWeixinuserService.findWeixinuserInfo(weixinOpenid);
            if (null != sjWeixinuser) {
                String token = tokenUtil.getToken(sjWeixinuser);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("token", token);
                dataMap.put("userInfo", sjWeixinuser);
                // 登录成功设置token过期时间
                redisCommonUtil.setCache(sjWeixinuser.getOpenid(), token, 60 * 60);
                return new ReturnString(dataMap);
            } else {
                // TODO: 相关注册操作
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录出错");
            return new ReturnString("登录出错");
        }
    }

    @UserLoginToken
    @PostMapping("test")
    public ReturnString test(String token) {
        Integer weixinuserId = tokenUtil.getWeixinuserId(token);
        System.out.println(weixinuserId);
        return new ReturnString(0, "你已通过验证");
    }
}