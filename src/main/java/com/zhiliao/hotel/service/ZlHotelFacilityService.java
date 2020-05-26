package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;

import java.util.List;
import java.util.Map;

public interface ZlHotelFacilityService {

    List<ZlHotelFacility> getHotelFacilityList(Integer hotelId);

    ZlHotelFacility getHotelFacilityDetail(Integer facilityId);

    ReturnString addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder);
}
