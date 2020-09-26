package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.wxuser.dto.UserVisitMenuLogDTO;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-22 17:57
 **/
public interface ZlUsermenulogService {

    void userVisitMenuLog(Long userid, UserVisitMenuLogDTO userVisitMenuLogDTO);

}
