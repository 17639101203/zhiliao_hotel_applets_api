package com.zhiliao.hotel.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zhiliao.hotel.model.ZlWxuser;
import org.springframework.stereotype.Component;


/**
 * Created by xiegege on 2019/11/14.
 */
@Component
public class TokenUtil {

    public String getToken(ZlWxuser wxuser) {
        return JWT.create()
                .withAudience(String.valueOf(wxuser.getUserid())) // 存入需要保存在token的信息
                .sign(Algorithm.HMAC256(wxuser.getWxopenid())); // 使用HMAC256生成token
    }

    public Integer getWxuserId(String token) {
        String wxuserId;
        try {
            // 获取token中的userId
            wxuserId = JWT.decode(token).getAudience().get(0);
            return Integer.parseInt(wxuserId);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
        }
    }
}