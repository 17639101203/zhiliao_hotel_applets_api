package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelgoodssku;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-28 09:55
 **/
public interface ZlHotelgoodsskuMapper extends Mapper<ZlHotelgoodssku> {
    Integer getStockCount(@Param("hotelID") Integer hotelID, @Param("hotelGoodsSkuID") Integer hotelGoodsSkuID);
}
