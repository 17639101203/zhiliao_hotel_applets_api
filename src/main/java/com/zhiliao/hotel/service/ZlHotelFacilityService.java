package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;

import java.util.List;
import java.util.Map;

public interface ZlHotelFacilityService {

    List<ZlHotelFacility> getHotelFacilityList(Integer hotelId);

    ZlHotelFacility getHotelFacilityDetail(Integer facilityId);

    Map<String, Object> addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder);
}
