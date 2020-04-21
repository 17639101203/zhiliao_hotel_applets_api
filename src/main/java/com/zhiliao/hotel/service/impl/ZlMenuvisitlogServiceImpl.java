package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlMenuvisitlogMapper;
import com.zhiliao.hotel.model.ZlMenuvisitlog;
import com.zhiliao.hotel.service.ZlMenuvisitlogService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.HttpContextUtils;
import com.zhiliao.hotel.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 邓菡晨
 * @Package com.zhiliao.hotel.service.impl
 * @Classname ZlUserloginlogServiceImpl
 * @date 2020/4/15 8:58
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlMenuvisitlogServiceImpl implements ZlMenuvisitlogService {

    private final ZlMenuvisitlogMapper zlMenuvisitlogMapper;

    @Autowired
    public ZlMenuvisitlogServiceImpl(ZlMenuvisitlogMapper zlMenuvisitlogMapper) {
        this.zlMenuvisitlogMapper = zlMenuvisitlogMapper;
    }

    /**
     * 添加菜单点击记录
     * @param menuId
     * @param userId
     */
    @Override
    public void add(Integer menuId, Integer userId) {

        ZlMenuvisitlog zlMenuvisitlog = new ZlMenuvisitlog();
        //菜单id
        zlMenuvisitlog.setMenuid(menuId);
        //用户id
        zlMenuvisitlog.setUserid(userId);
        //用户ip
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        zlMenuvisitlog.setUserip(IPUtils.getIpAddr(request));
        //创建时间
        zlMenuvisitlog.setCreatedate(DateUtils.javaToPhpNowDateTime());

        zlMenuvisitlogMapper.insertSelective(zlMenuvisitlog);

    }
}
