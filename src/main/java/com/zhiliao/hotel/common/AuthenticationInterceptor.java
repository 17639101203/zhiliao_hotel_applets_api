package com.zhiliao.hotel.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.JsonObject;
import com.zhiliao.hotel.controller.myOrder.util.StringUtils;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlWxuserService;
import com.zhiliao.hotel.service.impl.ZlRentCarGoodsServiceImpl;
import com.zhiliao.hotel.utils.AESUtil;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;


/**
 * 接口拦截器
 *
 * @PassToken 无需登录权限，直接放行
 * @NoLoginRequiredToken 无需登录权限，但需要接口保护。例如：发短信接口
 * @UserLoginToken 需要用户登录后访问的接口权限
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    /**
     * 公共密钥，保存在服务器，客户端不会知道密钥，以防被攻击
     * WX_USER_SECRET使用了AES加密
     */
    public static final String WX_USER_SECRET = "5E8DE3C1B420ACDBC1149F4819E7CF27";

    @Autowired
    private ZlWxuserService zlWxuserService;
    @Autowired
    private RedisCommonUtil redisCommonUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查是否有passToken注释，有则直接放行
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 从url中取出token
        String token = httpServletRequest.getHeader("token");

        logger.info("拦截器获取到的token: " + token);

        if (token == null || "".equals(token)) {
            setHttpServletResponseMessage(httpServletResponse, -1, "未接收到token信息!");
            return false;
        }

        // 检查是否有NoLoginRequiredToken注释，有则进行校验
        if (method.isAnnotationPresent(NoLoginRequiredToken.class)) {
            NoLoginRequiredToken noLoginRequiredToken = method.getAnnotation(NoLoginRequiredToken.class);
            if (noLoginRequiredToken.required()) {
                // 进行AES解密校验
                try {
                    String decrypt = AESUtil.decrypt(token);
                    logger.info("AES解密校验的decrypt: " + decrypt);
                    if ("".equals(decrypt)) {
                        setHttpServletResponseMessage(httpServletResponse, 403, "错误token（NoLoginRequiredToken），没有访问权限!");
                        return false;
                    }
                    // 判断解析出的时间，时间差是否大于小于10分钟
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long tokenDateTime = format.parse(decrypt).getTime();
                    //相差的分钟
                    long minute = (System.currentTimeMillis() - tokenDateTime) / (1000 * 60);
                    if (minute > 10 || minute < -10) {
                        setHttpServletResponseMessage(httpServletResponse, 403, "过期token（NoLoginRequiredToken），没有访问权限!");
                        return false;
                    }
                } catch (Exception e) {
                    setHttpServletResponseMessage(httpServletResponse, 403, "错误token（NoLoginRequiredToken），没有访问权限!");
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }

        // 检查是否有FlashToken注释，有则进行校验
        if (method.isAnnotationPresent(FlashToken.class)) {
            FlashToken flashToken = method.getAnnotation(FlashToken.class);
            if (flashToken.required()) {
                String userId;
                try {
                    // 获取token中的userId
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    setHttpServletResponseMessage(httpServletResponse, 405, "错误token，没有访问权限，请重新登录!");
                    e.printStackTrace();
                    return false;
                }
                // 查询用户是否存在
                ZlWxuser wxuser = zlWxuserService.findWxuserByUserId(Long.parseLong(userId));
                if (wxuser == null) {
                    setHttpServletResponseMessage(httpServletResponse, 405, "没有访问权限，用户不存在!");
                    return false;
                }
                // 对比redis缓存与用户传的token是否一致、是否过期
                String redisToken = (String) redisCommonUtil.getCache(wxuser.getWxopenid() + "flash");
//                System.err.println(redisToken);
//                System.err.println(token);
                if (redisToken == null || !token.equals(redisToken)) {
                    setHttpServletResponseMessage(httpServletResponse, 405, "没有访问权限，用户未登录或登录已过期!");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(WX_USER_SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    setHttpServletResponseMessage(httpServletResponse, 405, "错误token，没有访问权限，请重新登录!");
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }

        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                String userId;
                try {
                    // 获取token中的userId
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    setHttpServletResponseMessage(httpServletResponse, 401, "错误token，没有访问权限，请重新登录!");
                    e.printStackTrace();
                    return false;
                }
                // 查询用户是否存在
                ZlWxuser wxuser = zlWxuserService.findWxuserByUserId(Long.parseLong(userId));
                if (wxuser == null) {
                    setHttpServletResponseMessage(httpServletResponse, 401, "没有访问权限，用户不存在!");
                    return false;
                }
                // 对比redis缓存与用户传的token是否一致、是否过期
                String redisToken = (String) redisCommonUtil.getCache(wxuser.getWxopenid());
                if (redisToken == null || !token.equals(redisToken)) {
                    setHttpServletResponseMessage(httpServletResponse, 401, "没有访问权限，用户未登录或登录已过期!");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(WX_USER_SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    setHttpServletResponseMessage(httpServletResponse, 401, "错误token，没有访问权限，请重新登录!");
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void setHttpServletResponseMessage(HttpServletResponse httpServletResponse, Integer code, String message) {
        try {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code", code);
            jsonObject.addProperty("message", message);
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
