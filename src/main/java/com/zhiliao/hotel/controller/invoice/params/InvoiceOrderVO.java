package com.zhiliao.hotel.controller.invoice.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel("发票订单详情返回参数")
public class InvoiceOrderVO {


    /**
     * 发票订单ID
     */
    private Long invoiceorderid;

    /**
     * 发票类型:1:增值税普通发票;2增值税专用发票
     */
    @ApiModelProperty("发票类型:1:增值税普通发票;2增值税专用发票")
    private Byte invoicetype;

    /**
     * 发票类型:(主体)1: 个人;2单位
     */
    @ApiModelProperty("发票类型:(主体)1: 个人;2单位")
    private Byte mainbodytype;

    /**
     * 0电子发票；1纸质发票
     */
    @ApiModelProperty("0电子发票；1纸质发票")
    private Byte electronicpapertype;

    /**
     * 0电子发票；1纸质发票
     */
    @ApiModelProperty("酒店ID")
    private Integer hotelid;

    /**
     * 0电子发票；1纸质发票
     */
    @ApiModelProperty("酒店名称")
    private String hotelname;

    /**
     * 个人真实姓/或单位抬头
     */
    @ApiModelProperty("个人真实姓/或单位抬头")
    private String title;

    /**
     * 单位的纳税人识别号:15/18或20位
     */
    @ApiModelProperty("单位的纳税人识别号:15/18或20位")
    private String identifier;

    /**
     * 个人或单位电话号码
     */
    @ApiModelProperty("个人或单位电话号码")
    private String tel;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;

    /**
     * 开票状态 -1:已取消;0:未开票;1开票中;2已开票
     */
    @ApiModelProperty("开票状态 -1:已取消;0:未开票;1开票中;2已开票")
    private Byte invoicestatus;

    /**
     * 开户行
     */
    @ApiModelProperty("开户行")
    private String bank;

    /**
     * 开户行账号
     */
    @ApiModelProperty("开户行账号")
    private String bankaccountnumber;

    /**
     * 单位电话
     */
    @ApiModelProperty("单位电话")
    private String companytel;

    /**
     * 单位地址
     */
    @ApiModelProperty("单位地址")
    private String companyaddress;


    /**
     * 发票订单编号
     */
    @ApiModelProperty("发票订单编号")
    private String invoiceordernumber;


    /**
     * 添加时间
     */
    @ApiModelProperty("添加时间")
    private Integer createdate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Integer updatedate;
}
