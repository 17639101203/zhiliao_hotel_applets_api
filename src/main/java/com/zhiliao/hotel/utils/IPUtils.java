package com.zhiliao.hotel.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 邓菡晨
 * @Package com.zhiliao.hotel.utils
 * @Classname IPUtils
 * @date 2020/4/15 21:32
 */
public class IPUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
            if (ip != null) {
                ip = ip.split(",")[0].trim();
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
