package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.invoice.vo.InvoiceOrderToPhpVO;
import com.zhiliao.hotel.controller.myAppointment.dto.InvoiceOrderDTO;
import com.zhiliao.hotel.model.ZlInvoiceOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ZlInvoiceOrderMapper extends Mapper<ZlInvoiceOrder> {

    void insertInvoiceOrder(ZlInvoiceOrder zlInvoiceOrder);

    InvoiceOrderDTO queryInvoiceOrderdetail(@Param("invoiceorderid") Long invoiceorderid);

    void removeInvoiceOrder(@Param("invoiceorderid") Long invoiceorderid, @Param("updatedate") Integer updatedate);

    @Select("select count(*) from zl_invoiceorder where UserID = #{userId} and HotelID = #{hotelId} and IsDelete = 0 ")
    int selectCountInvoice(@Param("userId") Long userId, @Param("hotelId") Integer hotelId);

    void deleteInvoiceOrder(@Param("invoiceorderid") Long invoiceorderid, @Param("nowTime") Integer nowTime);

    InvoiceOrderToPhpVO selectToPhp(@Param("invoiceorderid") Long invoiceorderid);
}
