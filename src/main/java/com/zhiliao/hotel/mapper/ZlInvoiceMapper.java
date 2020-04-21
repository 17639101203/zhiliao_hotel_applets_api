package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlInvoiceMapper  {

    /**
     * 新增发票
     * @param invoice  发票对象
     * @return  返回1代表新增成功
     */
    Integer  insertInvoice(ZlInvoice invoice);

    /**
     * 根据用户ID查询发票
     * @param userid    用户ID
     * @return    发票对象
     */
    List<ZlInvoice> queryInvoiceByUserID(Long userid);


    /**
     * 根据用户ID，发票删除发票抬头
     * @param userid    用户ID
     * @param invoiceid    发票ID
     * @return  返回1 为删除成功
     */
    Integer deleteInvoice(@Param("userid") Long userid,@Param("invoiceid")Integer invoiceid);

}
