package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.model.ZlInvoiceOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ZlInvoiceOrderMapper extends Mapper<ZlInvoiceOrder> {

    void insertInvoiceOrder(ZlInvoiceOrder zlInvoiceOrder);

    InvoiceOrderVO queryInvoiceOrderdetail(@Param("userid") Long userid, @Param("invoiceordernumber") String invoiceordernumber);

    void removeInvoiceOrder(@Param("userid") Long userid, @Param("invoiceordernumber") String invoiceordernumber,
                          @Param("updatedate") Integer updatedate);
}
