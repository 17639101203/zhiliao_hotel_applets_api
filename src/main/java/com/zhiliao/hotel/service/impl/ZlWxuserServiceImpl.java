package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.ZlWxuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiegege on 2019/10/14.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlWxuserServiceImpl implements ZlWxuserService {

    private final ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    public ZlWxuserServiceImpl(ZlWxuserMapper zlWxuserMapper) {
        this.zlWxuserMapper = zlWxuserMapper;
    }

    @Override
    public ZlWxuser findWxuserByUserId(Long userId) {
        ZlWxuser wxuser = new ZlWxuser();
        wxuser.setUserid(userId);
        return zlWxuserMapper.selectOne(wxuser);
    }

    @Override
    public ZlWxuser findWxuserByWxOpenId(String openid) {
        ZlWxuser wxuser = new ZlWxuser();
        wxuser.setWxopenid(openid);
        return zlWxuserMapper.selectOne(wxuser);
    }

    @Override
    public void addWxuser(ZlWxuser wxuser) {
        zlWxuserMapper.insertSelective(wxuser);
    }

    @Override
    public void updateWxuser(ZlWxuser wxuser) {
        zlWxuserMapper.updateWxuser(wxuser);
    }

    @Override
    public void addWxuserdetail(ZlWxuserdetail zlWxuserdetail) {
        zlWxuserdetailMapper.insertSelective(zlWxuserdetail);
    }

    @Override
    public void updateWxuserdetail(ZlWxuserdetail zlWxuserdetail) {
        ZlWxuserdetail wxuserdetail2 = new ZlWxuserdetail();
        wxuserdetail2.setUserid(zlWxuserdetail.getUserid());
        ZlWxuserdetail wxuserdetail3 = zlWxuserdetailMapper.selectOne(wxuserdetail2);
        if (wxuserdetail3 == null) {
            zlWxuserdetailMapper.insertSelective(zlWxuserdetail);
        } else {
            zlWxuserdetailMapper.updateWxuserdetail(zlWxuserdetail);
        }


    }

}