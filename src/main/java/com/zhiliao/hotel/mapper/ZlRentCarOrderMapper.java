package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myAppointment.dto.ZlRentCarOrderDTO;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderToPhpVO;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderVO;
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

    ZlRentCarOrderDTO getRentCarOrderDetail(@Param("orderid") Long orderid);

    ZlRentCarOrder rentCarOrderDetail(@Param("orderid") Long orderid);

    int cancelRentCarOrder(@Param("orderID") Long orderID, @Param("updatedate") int updatedate);

    @Select("select count(*) from zl_rentcarorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountRentCar(@Param("userId")Long userId, @Param("hotelId")Integer hotelId);

    int dlRentCarOrder(ZlRentCarOrder rentCarOrder);

    RentCarOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);

    //ZlRentCarOrder findAllByCarNumber(@Param("hotelid") Integer hotelid, @Param("carnamer") String carnamer);
}
