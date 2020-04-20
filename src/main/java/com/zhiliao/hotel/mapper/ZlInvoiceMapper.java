package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.zlInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlInvoiceMapper  {

    /**
     * 新增发票
     * @param invoice  发票对象
     * @return  返回1代表新增成功
     */
    public  Integer  insertInvoice(zlInvoice invoice);

    /**
     * 根据用户ID查询发票
     * @param userid    用户ID
     * @return    发票对象
     */
    public List<zlInvoice> queryInvoiceByUserID(Long userid);


    /**
     * 根据用户ID，发票删除发票抬头
     * @param userid    用户ID
     * @param invoiceid    发票ID
     * @return  返回1 为删除成功
     */
    public Integer deleteInvoice(@Param("userid") Long userid,@Param("invoiceid")Integer invoiceid);

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
