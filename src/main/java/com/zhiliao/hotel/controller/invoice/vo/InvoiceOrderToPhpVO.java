package com.zhiliao.hotel.controller.invoice.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 15:05
 **/
@Data
public class InvoiceOrderToPhpVO {

    private Long InvoiceOrderID;//发票订单ID
    private Integer InvoiceID;//发票ID
    private Long UserID;//用户ID
    private Integer HotelID;//酒店ID
    private Byte InvoiceType;//发票类型:1:增值税普通发票;2增值税专用发票
    private Byte MainBodyType;//发票类型:(主体)1: 个人;2单位
    private Boolean ElectronicPaperType;//0电子发票；1纸质发票
    private String Title;//个人真实姓/或单位抬头
    private String Identifier;//单位的纳税人识别号:15/18或20位
    private String Tel;//个人或单位电话号码
    private String Remark;//备注信息
    private Byte InvoiceStatus;//开票状态 -1:已取消;0:未开票;1开票中;2已开票
    private String Bank;//开户行
    private String BankAccountNumber;//开户行账号
    private String CompanyTel;//单位电话
    private String CompanyAddress;//单位地址
    private String RoomNumber;//房间号
    private String InvoiceOrderNumber;//发票订单编号
    private String OperatorName;//操作人员
    private String OperatorIP;//操作人员IP
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Integer CreateDate;//添加时间
    private Integer UpdateDate;//修改时间

}
