package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlInvoiceOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ZlInvoiceService {

    /**
     * 新增发票
     * @param Invoice 发票对象
     */
    void addInvoice(ZlInvoice Invoice);

    /***
     * 根据用户查询发票信息
     * @param userid    用户ID
     * @return  发票对象集合
     */
    PageInfoResult<List<Map<String,Object>>> queryByUserID(Long userid,Integer pageNo,Integer pageSize);


    /**
     * 根据用户ID，发票删除发票抬头
     * @param userid    用户ID
     * @param invoiceid    发票ID
     *
     */
    void deleteInvoice(Long userid, Integer invoiceid);


    /**
     * 查询发票详情
     * @param userid
     * @param invoiceid
     * @return
     */
    Map<String,Object> findinvoicedetails(Long userid, Integer invoiceid);


    /**
     * 根据酒店ID查询开票二维码
     * @param hotelid
     * @return
     */
    Map<String,Object> findInvoiceQrCodeUrl(Integer hotelid);


    /**
     * 增加发票订单
     * @param zlInvoiceOrder
     */
    void addinvoiceOrder(ZlInvoiceOrder zlInvoiceOrder);


    /**
     * 修改开票信息
     * @param zlInvoice
     */
    void changeInvoice(ZlInvoice zlInvoice);


    /**
     * 查询开票订单详情
     * @param userid
     * @param invoiceordernumber
     * @return
     */
    InvoiceOrderVO findInvoiceOrderdetail(Long userid,String invoiceordernumber);


    /**
     * 取消开票预约
     * @param userid
     * @param invoiceordernumber
     * @param updatedate
     */
    void cancelInvoiceOrder(Long userid,String invoiceordernumber, Integer updatedate);
}


