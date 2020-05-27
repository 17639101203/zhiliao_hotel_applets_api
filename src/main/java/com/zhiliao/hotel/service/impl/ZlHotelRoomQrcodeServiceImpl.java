package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlHotelRoomQrcodeMapper;
import com.zhiliao.hotel.model.ZlHotelRoomQrcode;
import com.zhiliao.hotel.service.ZlHotelRoomQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ZlHotelRoomQrcodeServiceImpl implements ZlHotelRoomQrcodeService {

    @Autowired
    private ZlHotelRoomQrcodeMapper zlHotelRoomQrcodeMapper;

    @Override
    public ZlHotelRoomQrcode getRoomQrcodeId(String codeId) {
        ZlHotelRoomQrcode roomQrcodeId = zlHotelRoomQrcodeMapper.getRoomQrcodeId(codeId);
        if(roomQrcodeId!=null){
            return roomQrcodeId;
        }
        return null;
    }
}
