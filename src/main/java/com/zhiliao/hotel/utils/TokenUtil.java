package com.zhiliao.hotel.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zhiliao.hotel.model.SjWeixinuser;
import org.springframework.stereotype.Component;


/**
 * Created by xiegege on 2019/11/14.
 */
@Component
public class TokenUtil {

    public String getToken(SjWeixinuser sjWeixinuser) {
        return JWT.create()
                .withAudience(String.valueOf(sjWeixinuser.getId())) // 存入需要保存在token的信息
                .sign(Algorithm.HMAC256(sjWeixinuser.getOpenid())); // 使用HMAC256生成token
    }

    public Integer getWeixinuserId(String token) {
        String weixinuserId;
        try {
            // 获取token中的userId
            weixinuserId = JWT.decode(token).getAudience().get(0);
            return Integer.parseInt(weixinuserId);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
        }
    }
}