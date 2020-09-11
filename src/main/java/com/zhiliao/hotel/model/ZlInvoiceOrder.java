package com.zhiliao.hotel.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@Table(name = "zl_invoiceorder")
public class ZlInvoiceOrder implements Serializable {
    /**
     * 发票订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long invoiceorderid;

    /**
     * 发票ID
     */
    private Integer invoiceid;

    /**
     * zl_ordertype表ID
     */
    private Byte moldtype;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeforid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名
     */
    private String hotelname;

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
    private Boolean electronicpapertype;

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
     * 个人或单位邮箱
     */
    private String email;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 开票状态 -1:已取消;0:未开票;1开票中;2已开票;2已接单
     */
    private Byte invoicestatus;

    /**
     * 取消用户类型:1-用户,2-平台
     */
    private Byte cancelusertype;

    /**
     * 取消原因
     */
    private String cancelremark;

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
     * 操作人员
     */
    private String operatorname;

    /**
     * 操作人员IP
     */
    private String operatorip;

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