package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myOrder.util.IpUtils;
import com.zhiliao.hotel.controller.wxuser.dto.UserLoginLogDTO;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlUserloginlogMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlUserloginlog;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlUserloginlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlUserloginlogServiceImpl implements ZlUserloginlogService {

    @Autowired
    private ZlUserloginlogMapper zlUserloginlogMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Override
    public int insert(ZlUserloginlog zlUserloginlog) {
        return zlUserloginlogMapper.insert(zlUserloginlog);
    }

    @Override
    public void insertUserLog(HttpServletRequest request, Long userid, UserLoginLogDTO userLoginLogDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userid);
        zlWxuser = zlWxuserMapper.selectOne(zlWxuser);

        ZlUserloginlog zlUserloginlog = new ZlUserloginlog();
        zlUserloginlog.setUserid(userid);
        zlUserloginlog.setHotelid(userLoginLogDTO.getHotelID());
        if ((!(userLoginLogDTO.getRoomID() == null)) && StringUtils.isNoneBlank(userLoginLogDTO.getRoomNumber())) {
            ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(userLoginLogDTO.getRoomNumber(), userLoginLogDTO.getHotelID());
            if (zlHotelroom == null) {
                throw new BizException("该房间不存在,详情请咨询酒店前台!");
            }
            zlUserloginlog.setRoomid(userLoginLogDTO.getRoomID());
            zlUserloginlog.setRoomnumber(userLoginLogDTO.getRoomNumber());
        }
        zlUserloginlog.setComefromid((byte) 1);
        zlUserloginlog.setNickname(zlWxuser.getNickname());
        zlUserloginlog.setHotelname(userLoginLogDTO.getHotelName());

        String ipAddr = IpUtils.getIpAddr(request);
        zlUserloginlog.setLoginip(ipAddr);
        zlUserloginlog.setCreatedate(currertTime);

        zlUserloginlogMapper.insertSelective(zlUserloginlog);

    }
}
