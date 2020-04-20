package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.zlInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlInvoiceMyMapper {


    /**
     * 获取所有服务订单
     * @param userId
     * @param invoicestatus
     * @return
     */
    List<zlInvoice> findAllByUserId(@Param("userId") Long userId, @Param("invoicestatus") Integer invoicestatus);

    /**
     * 订单详情
     * @param invoiceid
     * @return
     */
    zlInvoice orderDetail(@Param("invoiceid") Integer invoiceid);
}
