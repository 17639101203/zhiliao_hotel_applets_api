package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlInvoice;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ZlInvoiceMapper extends Mapper<ZlInvoice> {

    /**
     * 新增发票
     *
     * @param invoice 发票对象
     * @return 返回1代表新增成功
     */
    Integer insertInvoice(ZlInvoice invoice);

    /**
     * 根据用户ID查询发票
     *
     * @param userid 用户ID
     * @return 发票对象
     */
    List<Map<String, Object>> queryInvoiceByUserID(@Param("userid") Long userid);


    /**
     * 根据用户ID，发票删除发票抬头
     *
     * @param invoiceid 发票ID
     * @return 返回1 为删除成功
     */
    Integer deleteInvoice(@Param("invoiceid") Integer invoiceid, @Param("updateDate") Integer updateDate);


    /**
     * 查询发票详情
     *
     * @param userid    用户ID
     * @param invoiceid 发票ID
     * @return 返回1 为删除成功
     */
    Map<String, Object> queryInvoicedetail(@Param("userid") Long userid, @Param("invoiceid") Integer invoiceid);


    /**
     * 根据酒店ID查询开票二维码
     *
     * @param hotelid
     * @return
     */
    String queryInvoiceQrCodeUrl(@Param("hotelid") Integer hotelid);

    /**
     * 修改开票信息
     *
     * @param zlInvoice
     */
    void updateInvoiceMsg(ZlInvoice zlInvoice);
}
