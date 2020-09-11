package com.zhiliao.hotel.controller.myOrder.vo;

import com.zhiliao.hotel.model.ZlOrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Data
public class OrderDetailsReturn {

    /**
     * 订单编号
     */
    private String orderserialno;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 客房编号
     */
    private String RoomNumber;

    /**
     * 总价
     */
    private BigDecimal totalprice;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallypay;

    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4挂账
     */
    private Byte paytype;

    /**
     * 配送地址(省市区合并完的详细地址), 不为空时表示土特产需要配送
     */
    private String deliveryaddress;

    /**
     * 快递ID
     */
    private Integer expressid;

    /**
     * 物流编号
     */
    private String tracknumber;

    /**
     * 支付状态：0无须支付;1:待支付;2已支付
     */
    private Byte paystatus;

    /**
     * 订单状态:-2报损;-1:已取消;0待确认;1已确认/已下单;2 已发货；3已签收/已完成;4最终完成(不能被操作),5已接单,100已结算
     */
    private Byte orderstatus;

    /**
     * 退款状态:-12退款关闭(不可再申述);-2退款被驳回;-1取消退款;0正常;1审核中;2审核通过;3退款中(退款发起);4已退款
     */
    private Byte refundstatus;

    /**
     * 退款发起次数
     */
    private Byte refundcount;

    /**
     * 优惠券金额
     */
    private BigDecimal couponcash;

    /**
     * 配送金额
     */
    private BigDecimal sendcash;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 送达时间;默认0表示尽快送达
     */
    private Integer deliverydate;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

    /**
     * 支付时间
     */
    private Integer paydate;

    /**
     * 发货时间
     */
    private Integer senddate;

    /**
     * 接单时间
     */
    private Integer receivedate;

    /**
     * 取消时间
     */
    private Integer canceldate;

    /**
     * 完成时间(已签收/已完成)
     */
    private Integer completedate;

    /**
     * 退款完成时间
     */
    private Integer refundfinishdate;


    private List<ZlOrderDetail> zlOrderDetailList;

    /**
     * 状态展示码
     */
    private Integer statusShowCount;

    /**
     * 状态展示信息
     */
    private String statusShowInfo;

    /**
     * 最新发起的退款时间
     */
    private Integer refunddate;

    /**
     * 用户申请退款备注
     */
    private String userremark;

    /**
     * 退款类型:0无;1用户退款;2酒店退款
     */
    private Byte refundusertype;

    /**
     * 退款金额
     */
    private BigDecimal refundmoney;

    /**
     * 是否使用优惠券
     */
    private Boolean isUseCoupon;

    /**
     * 退款金额
     */
    private BigDecimal couponCash;

    /**
     * 是否寄件
     */
    private Boolean ismail;

}
