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

    /**
     * 公共密钥，保存在服务器，客户端不会知道密钥，以防被攻击
     * WX_USER_SECRET使用了AES加密
     */
    public static final String WX_USER_SECRET = "5E8DE3C1B420ACDBC1149F4819E7CF27";

    public static String getToken(ZlWxuser wxuser) {
        return JWT.create()
                .withAudience(String.valueOf(wxuser.getUserid())) // 存入需要保存在token的信息
                .sign(Algorithm.HMAC256(WX_USER_SECRET)); // 使用HMAC256生成token
    }

    public static Long getUserId(String token) {
        String userId;
        try {
            // 获取token中的userId
            userId = JWT.decode(token).getAudience().get(0);
            return Long.parseLong(userId);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
        }
    }
}