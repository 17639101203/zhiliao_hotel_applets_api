package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlNews;


/**
 * 咨讯service接口
 */
public interface ZlNewsService {
    PageInfoResult findAllHoteId(Integer hotelID, Integer pageNo, Integer pageSize);

    ZlNews findById(Integer newsid);
}
