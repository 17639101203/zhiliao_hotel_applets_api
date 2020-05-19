package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlBanner;

import java.util.List;

/**
 * @author 邓菡晨
 * @date 2020/4/14 10:36
 */
public interface ZlBannerService {

    List<ZlBanner> findBanner(Integer hotelID, Integer menuID);
}
