package com.zhiliao.hotel.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ZlInvoiceOrder implements Serializable {
    /**
     * 发票订单ID
     */
    private Long invoiceorderid;

    /**
     * 发票ID
     */
    private Integer invoiceid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

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
     * 开票状态 -1:已取消;0:未开票;1开票中;2已开票
     */
    private Byte invoicestatus;

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
     * 房间号
     */
    private String roomnumber;

    /**
     * 发票订单编号
     */
    private String invoiceordernumber;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;



}