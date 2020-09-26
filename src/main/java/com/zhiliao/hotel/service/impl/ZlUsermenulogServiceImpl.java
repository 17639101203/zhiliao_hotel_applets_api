package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.wxuser.dto.UserVisitMenuLogDTO;
import com.zhiliao.hotel.mapper.ZlUsermenulogMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.mapper.ZlXcxMenuMapper;
import com.zhiliao.hotel.model.ZlUsermenulog;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.model.ZlXcxmenu;
import com.zhiliao.hotel.service.ZlUsermenulogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-22 17:57
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlUsermenulogServiceImpl implements ZlUsermenulogService {

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    private ZlUsermenulogMapper zlUsermenulogMapper;

    @Autowired
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Override
    public void userVisitMenuLog(Long userid, UserVisitMenuLogDTO userVisitMenuLogDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlXcxmenu zlXcxmenu = new ZlXcxmenu();
        zlXcxmenu.setMenuid(userVisitMenuLogDTO.getMenuId());
        zlXcxmenu.setMenustatus((byte) 1);
        zlXcxmenu.setIsdelete(false);
        zlXcxmenu = zlXcxMenuMapper.selectOne(zlXcxmenu);
        if (zlXcxmenu == null) {
            throw new BizException("参数有误!");
        }

        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userid);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        ZlUsermenulog zlUsermenulog = new ZlUsermenulog();
        zlUsermenulog.setUserid(userid);
        zlUsermenulog.setParentid(zlXcxmenu.getParentid());
        zlUsermenulog.setNickname(zlWxuserdetail.getRealname());
        zlUsermenulog.setHotelid(userVisitMenuLogDTO.getHotelID());
        zlUsermenulog.setHotelname(userVisitMenuLogDTO.getHotelName());
        zlUsermenulog.setLinkurl(zlXcxmenu.getLinkurl());
        zlUsermenulog.setIconurl(zlXcxmenu.getIconurl());
        zlUsermenulog.setCreatedate(currertTime);

        zlUsermenulogMapper.insertSelective(zlUsermenulog);
    }

}
