package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlNews;


/**
 * 咨讯service接口
 */
public interface ZlNewsService {
    PageInfoResult findAllHoteId(Integer pageNo, Integer pageSize, Integer hotelID);

    ZlNews findById(Integer newsid);
}
