package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlHotelFacility;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlHotelFacilityMapper extends Mapper<ZlHotelFacility> {
    List<ZlHotelFacility> getHotelFacilityList(@Param("hotelId") Integer hotelId);

    ZlHotelFacility getHotelFacilityDetail(@Param("facilityId") Integer facilityId);

    void updateCount(@Param("facilityID") Integer facilityID, @Param("time") Integer time);

    int updateByFacilityId(@Param("facilityId") Integer facilityId, @Param("updateDate") Integer updateDate);
}
