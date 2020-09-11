package com.zhiliao.hotel.controller.myAppointment.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-24 17:33
 **/
@Data
public class InvoiceOrderDTO implements Serializable {

    /**
     * 发票订单ID
     */
    private Long invoiceorderid;

    /**
     * 发票类型:1:增值税普通发票;2增值税专用发票
     */
    private Byte invoicetype;

    /**
     * 发票类型:(主体)1: 个人;2单位
     */
    private Byte mainbodytype;

    /**
     * 0电子发票；1纸质发票
     */
    private Byte electronicpapertype;

    /**
     * 0电子发票；1纸质发票
     */
    private Integer hotelid;

    /**
     * 0电子发票；1纸质发票
     */
    private String hotelname;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 个人真实姓/或单位抬头
     */
    private String title;

    /**
     * 单位的纳税人识别号:15/18或20位
     */
    private String identifier;

    /**
     * 个人或单位电话号码
     */
    private String tel;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 开票状态 -1:已取消;0:未开票;1开票中;2已开票;;3已接单
     */
    private Byte invoicestatus;

    /**
     * 状态信息
     */
    private String statusmessage;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 开户行账号
     */
    private String bankaccountnumber;

    /**
     * 单位电话
     */
    private String companytel;

    /**
     * 单位地址
     */
    private String companyaddress;


    /**
     * 发票订单编号
     */
    private String invoiceordernumber;


    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

}
