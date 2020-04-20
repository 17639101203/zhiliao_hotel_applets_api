package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.zlNews;

import java.util.List;

/**
 * 咨讯service接口
 */
public interface zlNewsService {
    PageInfoResult findAllJiuDianId(Integer pageNo, Integer pageSize, Integer hotelID, Integer type, Integer status);

    zlNews findById(Integer newsid);
}
