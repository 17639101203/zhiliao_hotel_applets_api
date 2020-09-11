package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.eatRent.vo.ZlDisburdeneatliveVO;
import com.zhiliao.hotel.mapper.ZlDisburdeneatliveMapper;
import com.zhiliao.hotel.mapper.ZlUserloginlogMapper;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import com.zhiliao.hotel.model.ZlUserloginlog;
import com.zhiliao.hotel.service.ZlDisburdeneatliveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlDisburdeneatliveServiceImpl implements ZlDisburdeneatliveService {

    @Autowired
    private ZlDisburdeneatliveMapper disburdeneatliveMapper;

    @Autowired
    private ZlUserloginlogMapper zlUserloginlogMapper;

    @Override
    public ZlDisburdeneatliveVO findEatRentInfo(Integer hotelId, Integer roomId) {
        ZlDisburdeneatliveVO zlDisburdeneatliveVO = disburdeneatliveMapper.find(hotelId);
        if (zlDisburdeneatliveVO == null) {
            return null;
        }
        //更新浏览量
        disburdeneatliveMapper.addVisitCount(zlDisburdeneatliveVO.getRecid());

        ZlUserloginlog zlUserloginlog = new ZlUserloginlog();
        zlUserloginlog.setHotelid(hotelId);
        zlUserloginlog.setRoomid(roomId);
        int count = zlUserloginlogMapper.selectCount(zlUserloginlog);
        zlDisburdeneatliveVO.setCheckInCount(count + zlDisburdeneatliveVO.getFloatvalue());

        return zlDisburdeneatliveVO;
    }

}
