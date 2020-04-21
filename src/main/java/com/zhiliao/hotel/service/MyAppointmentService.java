package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;

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
     * 清扫订单详情
     * @param orderID
     * @return
     */
    ZlCleanOrder cleanOrderDetail(Long orderID);

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
     * 发票订单详情
     * @param invoiceid
     * @return
     */
    ZlInvoice invoiceOrderDetail(Integer invoiceid);

    /**
     * 获取报修订单
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult repairFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize);

    /**
     * 报修订单详情
     * @param orderID
     * @return
     */
    ZlRepairorder repairOrderDetail(Long orderID);
}
