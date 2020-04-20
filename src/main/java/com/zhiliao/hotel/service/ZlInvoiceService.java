package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlInvoice;

import java.util.List;


public interface ZlInvoiceService {

    /**
     * 新增发票
     * @param Invoice 发票对象
     */
    public void addInvoice(ZlInvoice Invoice);

    /***
     * 根据用户查询发票信息
     * @param userid    用户ID
     * @return  发票对象集合
     */
    public List<ZlInvoice> queryByUserID(Long userid);


    /**
     * 根据用户ID，发票删除发票抬头
     * @param userid    用户ID
     * @param invoiceid    发票ID
     *
     */
    public void deleteInvoice(Long userid, Integer invoiceid);

}


