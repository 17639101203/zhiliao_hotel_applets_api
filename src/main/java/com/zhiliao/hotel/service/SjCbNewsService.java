package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.SjCbNews;

import java.util.List;

/**
 * 咨讯service接口
 */
public interface SjCbNewsService {
    List<SjCbNews> findAllJiuDianId(Integer jiudianid);

    SjCbNews findById(Integer id);
}
