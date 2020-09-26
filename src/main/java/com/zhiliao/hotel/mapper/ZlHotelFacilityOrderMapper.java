package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.controller.hotelfacility.vo.ZlHotelFacilityOrderToPhpVO;
import com.zhiliao.hotel.controller.myAppointment.dto.HotelFacilityOrderParamDTO;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ZlHotelFacilityOrderMapper extends Mapper<ZlHotelFacilityOrder> {
    List<ZlHotelFacilityOrder> findAllOrder(@Param("userId") Long userId,
                                            @Param("orderstatus") Byte orderstatus,
                                            @Param("hotelId") Integer hotelId
    );

    ZlHotelFacilityOrder findOrderById(@Param("orderID") Long orderID);

    HotelFacilityOrderParamDTO findOrderByIdVO(@Param("orderID") Long orderID);

    int byOrderId(ZlHotelFacilityOrder order);

    //void addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder);

    ZlHotelFacilityOrder findByBiginAndEndDate(@Param("beginusedate") Integer beginusedate,
                                               @Param("endusedate") Integer endusedate,
                                               @Param("hotelid") Integer hotelid,
                                               @Param("facilityid") Integer facilityid);

    @Select("select count(*) from zl_hotelfacilityorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountFacility(@Param("userId")Long userId, @Param("hotelId")Integer hotelId);

    void updateById(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    void userDeleteFacilityOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    ZlHotelFacilityOrderToPhpVO selectToPhp(@Param("orderid") Long orderid);
}
