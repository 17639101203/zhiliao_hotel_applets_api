package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRentCarOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 15:12
 **/
public interface ZlRentCarOrderMapper extends Mapper<ZlRentCarOrder> {

    ZlRentCarOrder rentCarOrderDetail(long orderid);

    int updateById(ZlRentCarOrder rentCarOrder);

    @Select("select count(*) from zl_rentcarorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountRentCar(Long userId);

    int dlRentCarOrder(ZlRentCarOrder rentCarOrder);
    //ZlRentCarOrder findAllByCarNumber(@Param("hotelid") Integer hotelid, @Param("carnamer") String carnamer);
}
