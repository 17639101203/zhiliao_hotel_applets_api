package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
public interface ZlCleanOrderMyService {


    /**
     * 获取所有清扫订单
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult findAllByStatus(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);

    /**
     * 订单详情
     * @param orderID
     * @return
     */
    ZlCleanOrder orderDetail(Long orderID);
}
