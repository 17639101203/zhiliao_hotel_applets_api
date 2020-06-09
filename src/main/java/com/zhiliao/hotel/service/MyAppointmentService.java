package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;

import java.util.Map;

public interface MyAppointmentService {
    /**
     * 获取所有清扫订单
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult cleanFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);


    /**
     * 获取发票订单
     * @param userId
     * @param invoicestatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult invoiceFindAll(Long userId, Integer invoicestatus, Integer pageNo, Integer pageSize);


    /**
     * 获取报修订单
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult repairFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);


    void cancelOrder(Long orderid, Integer orderServiceType);

    PageInfoResult serviceFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);

    Map<String, Integer> myAppointementCount(Long userId);

    PageInfoResult findAllWakeOrder(Long userId, Integer orderStatus, Integer pageNo, Integer pageSize);

    PageInfoResult findAllRentCarOrder(Long userId, Integer orderStatus, Integer pageNo, Integer pageSize);

    PageInfoResult findAllCheckOutOrder(Long userId, Integer orderStatus, Integer pageNo, Integer pageSize);

    PageInfoResult findAllContinueLiveOrder(Long userId, Integer orderStatus, Integer pageNo, Integer pageSize);
}
