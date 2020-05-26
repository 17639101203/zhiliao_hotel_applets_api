package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;

import java.util.List;
import java.util.Map;


public interface ZlCleanOrderService {
    /**
     * 清扫下单
     * @param zlCleanOrder
     * @return
     */
     Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    /**
     * 清扫订单详情
     * @param userID
     * @param serialNumber
     * @return
     */
    Map<String,Object> selectCleanDetails(Long userID, String serialNumber);

    /**
     *取消清扫预约
     */
    void removeCleanOrder(Long userID, String serialNumber,Integer updatedate);
}
