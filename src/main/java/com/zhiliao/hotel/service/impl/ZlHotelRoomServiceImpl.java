package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.service.ZlHotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *   酒店客房业务实现类
 * @author chenrong
 * @created date 2020/4/14
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
    public Map<String, String> findWiFi(Integer hotelid) {
        Map<String, String> wifi =  zlHotelRoomMapper.findWiFi(hotelid);
        return wifi;
    }
}
