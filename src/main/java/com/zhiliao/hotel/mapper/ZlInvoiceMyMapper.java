package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZlInvoiceMyMapper {


    /**
     * 获取所有服务订单
     * @param userId
     * @param invoicestatus
     * @return
     */
    List<ZlInvoice> findAllByUserId(@Param("userId") Long userId, @Param("invoicestatus") Integer invoicestatus);

}
