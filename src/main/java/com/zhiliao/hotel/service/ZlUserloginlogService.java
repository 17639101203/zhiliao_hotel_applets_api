package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.wxuser.dto.UserLoginLogDTO;
import com.zhiliao.hotel.model.ZlUserloginlog;

import javax.servlet.http.HttpServletRequest;

public interface ZlUserloginlogService {

    int insert(ZlUserloginlog zlUserloginlog);

    void insertUserLog(HttpServletRequest request, Long userid, UserLoginLogDTO userLoginLogDTO);
}
