package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.myAppointment.result.ZlServiceorderResult;
import com.zhiliao.hotel.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-27 10:12
 **/
public interface MyAppointmentMapper {
    //清扫订单列表
    List<ZlCleanOrder> findAllClean(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    /**
     * 获取所有发票服务订单
     *
     * @param userId
     * @param invoicestatus
     * @return
     */
    List<InvoiceOrderVO> findAllInvoice(@Param("userId") Long userId, @Param("invoicestatus") Byte invoicestatus, @Param("hotelId") Integer hotelId);

    //报修订单列表
    List<ZlRepairorder> findAllRepair(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    //客房服务订单列表
    List<ZlServiceorderResult> serviceFindAll(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    //叫醒服务订单列表
    List<ZlWakeOrder> findAllWakeOrder(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    //租车服务订单列表
    List<ZlRentCarOrder> findAllRentCarOrder(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    //续住服务订单列表
    List<ZlContinueLiveOrder> findAllContinueLiveOrder(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);

    //退房服务订单列表
    List<ZlCheckoutOrder> findAllCheckOutOrder(@Param("userId") Long userId, @Param("orderstatus") Byte orderstatus, @Param("hotelId") Integer hotelId);
}
