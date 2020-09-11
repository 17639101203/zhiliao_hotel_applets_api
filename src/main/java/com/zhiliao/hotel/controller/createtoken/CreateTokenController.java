package com.zhiliao.hotel.controller.createtoken;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-21 10:18
 **/
@Api(tags = "创建Token_姬慧慧")
@RestController
@RequestMapping("createToken")
public class CreateTokenController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "创建Token_姬慧慧")
    @GetMapping
    @PassToken
    @ResponseBody
    public ReturnString findOrderDetail() {
        ZlWxuser wxuser = new ZlWxuser();
        wxuser.setUserid((long) 15622);
        wxuser.setWxopenid("ocxSw4pXf1E0qXJ3XnjbNh5EzrtQ");
        Map<String, Object> dataMap = new HashMap<>();
        //获取登录Token
        String token = TokenUtil.getToken(wxuser);
        //获取刷新Token
        String flashToken = TokenUtil.getFlashToken(wxuser);
        // 登录成功设置token过期时间
        redisTemplate.opsForValue().set(wxuser.getWxopenid(), token, RedisKeyConstant.USERTOKENTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(wxuser.getWxopenid() + "flash", flashToken, RedisKeyConstant.USERFLASHTIME, TimeUnit.SECONDS);
        dataMap.put("token", token);
        dataMap.put("flashToken", flashToken);
        return new ReturnString(dataMap);
    }

}
