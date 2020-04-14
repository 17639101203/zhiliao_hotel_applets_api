package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlWxuser;

/**
 * Created by xiegege on 2019/10/14.
 */
public interface ZlWxuserService {

    ZlWxuser findWxuserByWxuserId(Long wxuserId);

    ZlWxuser findWxuserByWxOpenId(String openid);

    void addWxuser(ZlWxuser wxuser);
}