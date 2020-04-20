package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.zlRepair;
import com.zhiliao.hotel.model.zlRepairorder;

import java.io.IOException;

public interface ZlRepairMyService {


    PageInfoResult findAllByUserId(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);

    zlRepairorder orderDetail(Long orderID);
}
