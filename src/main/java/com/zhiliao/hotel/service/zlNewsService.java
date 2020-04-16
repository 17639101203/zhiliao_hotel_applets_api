package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.zlNews;

import java.util.List;

/**
 * 咨讯service接口
 */
public interface zlNewsService {
    List<zlNews> findAllJiuDianId(Integer hotelID, Integer type, Integer status);

    zlNews findById(Integer newsid);
}
