package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlHotelFacilityOrderMapper extends Mapper<ZlHotelFacilityOrder> {
    List<ZlHotelFacilityOrder> findAllOrder(@Param("userId") Long userId,
                                            @Param("orderStatus") Integer orderStatus,
                                            @Param("payStatus") Integer payStatus,
                                            @Param("payType") Integer payType);

    ZlHotelFacilityOrder findOrderById(@Param("orderID") Long orderID);

    void byOrderId(ZlHotelFacilityOrder order);

    void addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder);
}
