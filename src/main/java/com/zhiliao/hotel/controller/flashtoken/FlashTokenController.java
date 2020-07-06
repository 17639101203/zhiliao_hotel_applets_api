package com.zhiliao.hotel.controller.flashtoken;

import com.zhiliao.hotel.common.FlashToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-04 14:54
 **/
@Api(tags = "刷新登录Token_姬慧慧")
@RestController
@RequestMapping("flashToken")
public class FlashTokenController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @ApiOperation(value = "刷新登录Token_姬慧慧")
    @PostMapping("getNewToken")
    @FlashToken
    @ResponseBody
    public ReturnString findOrderDetail(HttpServletRequest request) {
        try {
            String flashToken = request.getHeader("token");
            Long userId = TokenUtil.getFlashUserId(flashToken);
            //获取登录Token
            ZlWxuser wxuser = new ZlWxuser();
            wxuser.setUserid(userId);
            String token = TokenUtil.getToken(wxuser);
            ZlWxuser zlWxuser = zlWxuserMapper.selectOne(wxuser);
            redisTemplate.opsForValue().set(zlWxuser.getWxopenid(), token, RedisKeyConstant.USERTOKENTIME, TimeUnit.SECONDS);
            HashMap<String, String> map = new HashMap();
            map.put("token", token);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("刷新登录Token失败");
        }
    }

}
