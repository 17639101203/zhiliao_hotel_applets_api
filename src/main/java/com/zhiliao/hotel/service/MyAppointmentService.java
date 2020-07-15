package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;

import java.util.Map;

public interface MyAppointmentService {
    /**
     * 获取所有清扫订单
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult cleanFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId);


    /**
     * 获取发票订单
     *
     * @param userId
     * @param invoiceStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult invoiceFindAll(Long userId, Byte invoiceStatus, Integer pageNo, Integer pageSize, Integer hotelId);


    /**
     * 获取报修订单
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult repairFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId);

    void cancelOrder(Long orderid, Integer orderServiceType);

    PageInfoResult serviceFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId);

    Map<String, Integer> myAppointementCount(Long userId);

    PageInfoResult findAllWakeOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize,Integer hotelId);

    PageInfoResult findAllRentCarOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize,Integer hotelId);

    PageInfoResult findAllCheckOutOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize,Integer hotelId);

    PageInfoResult findAllContinueLiveOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize,Integer hotelId);
}
