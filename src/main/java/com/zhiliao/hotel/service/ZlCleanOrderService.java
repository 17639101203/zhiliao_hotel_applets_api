package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlCleanOrder;

import java.util.List;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
public interface ZlCleanOrderService {
    /*
        点赞吐槽
     */
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    /**
     * 获取所有清扫订单
     * @param userId
     * @param orderstatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ZlCleanOrder> findAllByStatus(Long userId, Integer orderstatus, Integer pageNum, Integer pageSize);

    /**
     * 订单详情
     * @param orderID
     * @return
     */
    ZlCleanOrder orderDetail(Long orderID);
}
