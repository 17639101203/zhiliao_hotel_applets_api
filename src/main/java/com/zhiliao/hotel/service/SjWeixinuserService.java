package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.SjWeixinuser;

/**
 * Created by xiegege on 2019/10/14.
 */
public interface SjWeixinuserService {

    SjWeixinuser findWeixinuserInfo(String weixinOpenid);

    SjWeixinuser findWeixinuserById(Integer weixinuserId);
}