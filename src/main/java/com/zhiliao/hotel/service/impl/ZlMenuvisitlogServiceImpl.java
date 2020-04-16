package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlMenuvisitlogMapper;
import com.zhiliao.hotel.model.ZlMenuvisitlog;
import com.zhiliao.hotel.service.ZlMenuvisitlogService;
import com.zhiliao.hotel.utils.HttpContextUtils;
import com.zhiliao.hotel.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 邓菡晨
 * @Package com.zhiliao.hotel.service.impl
 * @Classname ZlUserloginlogServiceImpl
 * @date 2020/4/15 8:58
 */
@Service
public class ZlMenuvisitlogServiceImpl implements ZlMenuvisitlogService {

    @Autowired
    private ZlMenuvisitlogMapper zlMenuvisitlogMapper;

    /**
     * 添加菜单点击记录
     * @param menuId
     * @param userId
     */
    @Override
    public void add(Integer menuId, Integer userId) {

        ZlMenuvisitlog zlMenuvisitlog = new ZlMenuvisitlog();
        //菜单id
        zlMenuvisitlog.setMenuId(menuId);
        //用户id
        zlMenuvisitlog.setUserId(userId);
        //用户ip
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        zlMenuvisitlog.setUserIp(IPUtils.getIpAddr(request));
        //创建时间
        zlMenuvisitlog.setCreateDate(System.currentTimeMillis()/1000);

        zlMenuvisitlogMapper.insertSelective(zlMenuvisitlog);

    }
}
