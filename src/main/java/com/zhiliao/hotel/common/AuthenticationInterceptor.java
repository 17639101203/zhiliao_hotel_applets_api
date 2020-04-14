package com.zhiliao.hotel.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlWxuserService;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

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
        // 检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 从url中取出token
                String token = httpServletRequest.getParameter("token");
                // 执行认证
                if (token == null || "".equals(token)) {
                    throw new RuntimeException("无token，请重新登录");
                }
                String wxuserId;
                try {
                    // 获取token中的weixinuserId
                    wxuserId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
                }
                // 查询用户是否存在
                ZlWxuser wxuser = zlWxuserService.findWxuserByWxuserId(Long.parseLong(wxuserId));
                if (wxuser == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 对比redis缓存与用户传的token是否一致、是否过期
                String redisToken = (String) redisCommonUtil.getCache(wxuser.getWxopenid());
                if (redisToken == null || !token.equals(redisToken)) {
                    throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(wxuser.getWxopenid())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401 错误token，没有访问权限，请重新登录");
                }
                return true;
            }
        }
        return false;
    }
}