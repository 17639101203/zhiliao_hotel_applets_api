package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;

import java.util.List;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
public interface ZlCleanOrderService {
    /**
     * 清扫下单
     * @param zlCleanOrder
     * @return
     */
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    /**
     * 清扫订单详情
     * @param userID
     * @param serialNumber
     * @return
     */
    public ZlCleanOrder selectCleanDetails(Long userID, String serialNumber);
}
