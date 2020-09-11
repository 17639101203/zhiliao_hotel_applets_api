package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-13 10:09
 **/
@Data
public class ShopOrderToPhp {

    /**
     * 订单ID
     */
    private Long OrderID;

    /**
     * 订单编号
     */
    private String OrderSerialNo;

    /**
     * 酒店ID
     */
    private Integer HotelID;

    /**
     * 酒店名
     */
    private String HotelName;

    /**
     * 房间ID
     */
    private Integer RoomID;

    /**
     * 房间号
     */
    private String RoomNumber;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short BelongModule;

    /**
     * 总价
     */
    private BigDecimal TotalPrice;

    /**
     * 下单时间
     */
    private Integer CreateDate;

    /**
     * 支付状态：0无须支付;1:待支付;2已支付
     */
    private Byte PayStatus;

    /**
     * 订单状态:-2报损;-1:已取消;0待确认;1已确认/已下单;2 已发货；3已签收/已完成;4最终完成(不能被操作),5已接单,100已结算
     */
    private Byte OrderStatus;

    /**
     * 退款状态:-2退款被驳回;-1用户取消退款;0正常;1审核中;2审核通过;3退款中(退款发起);4已退款
     */
    private Byte RefundStatus;

    /**
     * 备注信息
     */
    private String Remark;

}
