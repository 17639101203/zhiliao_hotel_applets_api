package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.SjWeixinuserMapper;
import com.zhiliao.hotel.model.SjWeixinuser;
import com.zhiliao.hotel.service.SjWeixinuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiegege on 2019/10/14.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class SjWeixinuserServiceImpl implements SjWeixinuserService {

    private SjWeixinuserMapper sjWeixinuserMapper;

    @Autowired
    public SjWeixinuserServiceImpl(SjWeixinuserMapper sjWeixinuserMapper) {
        this.sjWeixinuserMapper = sjWeixinuserMapper;
    }

    @Override
    public SjWeixinuser findWeixinuserById(Integer weixinuserId) {
        SjWeixinuser sjWeixinuser = new SjWeixinuser();
        sjWeixinuser.setId(weixinuserId);
        return sjWeixinuserMapper.selectOne(sjWeixinuser);
    }

    @Override
    public SjWeixinuser findWeixinuserByOpenId(String openid) {
        SjWeixinuser sjWeixinuser = new SjWeixinuser();
        sjWeixinuser.setOpenid(openid);
        return sjWeixinuserMapper.selectOne(sjWeixinuser);
    }
}