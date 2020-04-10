package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.SjWeixinuser;

/**
 * Created by xiegege on 2019/10/14.
 */
public interface SjWeixinuserService {

    SjWeixinuser findWeixinuserById(Integer weixinuserId);

    SjWeixinuser findWeixinuserByOpenId(String openid);
}