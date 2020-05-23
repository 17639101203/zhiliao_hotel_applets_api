package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 17:13
 **/
public interface ZlHotelDetailMapper extends Mapper<ZlHotelDetail> {

    BigDecimal findSendCashByHotelID(@Param("hotelID") Integer hotelID);

}
