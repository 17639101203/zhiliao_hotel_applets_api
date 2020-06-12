package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ZlHotelFacilityOrderMapper extends Mapper<ZlHotelFacilityOrder> {
    List<ZlHotelFacilityOrder> findAllOrder(@Param("userId") Long userId,
                                            @Param("orderStatus") Byte orderStatus
    );

    ZlHotelFacilityOrder findOrderById(@Param("orderID") Long orderID);

    HotelFacilityOrderParamVO findOrderByIdVO(@Param("orderID") Long orderID);

    int byOrderId(ZlHotelFacilityOrder order);

    //void addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder);

    ZlHotelFacilityOrder findByBiginAndEndDate(@Param("beginusedate") Integer beginusedate,
                                               @Param("endusedate") Integer endusedate,
                                               @Param("hotelid") Integer hotelid,
                                               @Param("facilityid") Integer facilityid);

    @Select("select count(*) from zl_hotelfacilityorder where UserID = #{userId} and IsDelete = 0 and IsUserDelete = 0")
    int selectCountFacility(Long userId);

    void updateById(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);

    void userDeleteFacilityOrder(@Param("orderID") Long orderID, @Param("updateDate") Integer updateDate);
}
