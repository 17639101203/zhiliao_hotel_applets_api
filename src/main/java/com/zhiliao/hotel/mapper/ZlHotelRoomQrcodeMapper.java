package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelRoomQrcode;
import org.apache.ibatis.annotations.Param;

/**
 * 二维码Mapper
 */
public interface ZlHotelRoomQrcodeMapper {

    public ZlHotelRoomQrcode getRoomQrcodeId(@Param("codeId") String codeId);
}
