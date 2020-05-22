package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelGoods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-21 15:59
 **/
public interface ZlHotelGoodsMapper extends Mapper<ZlHotelGoods> {
    Integer getStockCount(@Param("hotelID") Integer hotelID, @Param("skuID") Integer skuID);
}
