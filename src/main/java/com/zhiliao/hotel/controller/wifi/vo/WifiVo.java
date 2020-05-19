package com.zhiliao.hotel.controller.wifi.vo;

import java.io.Serializable;

/**
 * @author 邓菡晨
 * @date 2020/5/19 16:39
 */

public class WifiVo implements Serializable {

    private String WiFiName;
    private String WiFiPwd;

    public String getWiFiName() {
        return WiFiName;
    }

    public void setWiFiName(String wiFiName) {
        WiFiName = wiFiName;
    }

    public String getWiFiPwd() {
        return WiFiPwd;
    }

    public void setWiFiPwd(String wiFiPwd) {
        WiFiPwd = wiFiPwd;
    }
}
