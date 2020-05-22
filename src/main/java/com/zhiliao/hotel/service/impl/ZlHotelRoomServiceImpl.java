package com.zhiliao.hotel.service.impl;

import com.google.common.collect.Lists;
import com.zhiliao.hotel.controller.wifi.vo.WifiVo;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.service.ZlHotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 邓菡晨
 * @date 2020/4/14
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelRoomServiceImpl implements ZlHotelRoomService {


    private final ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    public ZlHotelRoomServiceImpl(ZlHotelRoomMapper zlHotelRoomMapper) {
        this.zlHotelRoomMapper = zlHotelRoomMapper;
    }

    @Override
    public List<WifiVo> findWiFi(Integer hotelID) {
        List<WifiVo> wifi =  zlHotelRoomMapper.findWiFi(hotelID);
        if(wifi!=null && wifi.size()>0){
            return wifi;
        }
        return Lists.newArrayList();
    }
}
