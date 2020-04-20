package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.zlInvoice;

import java.util.List;


public interface ZlInvoiceMyService {


    /**
     * 获取发票订单
     * @param userId
     * @param invoicestatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfoResult findAllByUserId(Long userId, Integer invoicestatus, Integer pageNo, Integer pageSize);

    /**
     * 订单详情
     * @param invoiceid
     * @return
     */
    zlInvoice orderDetail(Integer invoiceid);
}


