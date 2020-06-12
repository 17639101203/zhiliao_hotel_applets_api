package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.clean.cleanparm.CleanParm;
import com.zhiliao.hotel.model.ZlCleanOrder;

import java.util.List;
import java.util.Map;


public interface ZlCleanOrderService {

    /**
     * 清扫下单
     *
     * @param userid
     * @param cleanParm
     * @return
     */
    Map<String, Object> addCleanOrder(Long userid, CleanParm cleanParm);

    /**
     * 清扫订单详情
     *
     * @param orderID
     * @return
     */
    Map<String, Object> selectCleanDetails(Long orderID);

    /**
     * 取消清扫预约
     */
    void removeCleanOrder(Long orderID, Integer updatedate);
}
