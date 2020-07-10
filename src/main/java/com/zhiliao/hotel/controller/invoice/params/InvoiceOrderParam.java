package com.zhiliao.hotel.controller.invoice.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ApiModel("发票订单下单参数")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOrderParam {

    /**
     * 发票类型:1:增值税普通发票;2增值税专用发票
     */
    @ApiModelProperty(value = "发票类型:1:增值税普通发票;2增值税专用发票", required = true)
    private Byte invoicetype;

    /**
     * 发票类型:(主体)1: 个人;2单位
     */
    @ApiModelProperty(value = "发票类型:(主体)1: 个人;2单位", required = true)
    private Byte mainbodytype;


    /**
     * 个人真实姓/或单位抬头
     */
    @ApiModelProperty(value = "个人真实姓/或单位抬头", required = true)
    private String title;


    /**
     * 单位的纳税人识别号:15/18或20位
     */
    @ApiModelProperty(value = "单位的纳税人识别号:15/18或20位", required = true)
    private String identifier;


    /**
     * 个人或单位电话号码
     */
    @ApiModelProperty(value = "个人或单位电话号码", required = true)
    private String tel;


    /**
     * 0电子发票；1纸质发票
     */
    @ApiModelProperty(value = "0电子发票；1纸质发票", required = true)
    private Boolean electronicpapertype;


    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行", required = true)
    private String bank;

    /**
     * 开户行账号
     */
    @ApiModelProperty(value = "开户行账号", required = true)
    private String bankaccountnumber;


    /**
     * 单位电话
     */
    @ApiModelProperty(value = "单位电话", required = true)
    private String companytel;


    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址", required = true)
    private String companyaddress;


    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", required = false)
    private String remark;


    @ApiModelProperty(value = "发票ID")
    private Integer invoiceid;

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店ID")
    private Integer hotelid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String roomnumber;
}
