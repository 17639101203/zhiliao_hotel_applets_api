package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlRepairorder;

public interface ZlRepairMyService {


    PageInfoResult findAllByUserId(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);

    ZlRepairorder orderDetail(Long orderID);
}
