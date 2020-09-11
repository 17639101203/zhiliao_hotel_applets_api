package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.model.ZlWxuserdetail;

/**
 * Created by xiegege on 2019/10/14.
 */
public interface ZlWxuserService {

    ZlWxuser findWxuserByUserId(Long userId);

    ZlWxuser findWxuserByWxOpenId(String openid);

    void addWxuser(ZlWxuser wxuser);

    void updateWxuser(ZlWxuser wxuser);

    void addWxuserdetail(ZlWxuserdetail zlWxuserdetail);

    void updateWxuserdetail(ZlWxuserdetail zlWxuserdetail);
}