package com.zhiliao.hotel.controller.myOrder.vo;

import com.zhiliao.hotel.model.ZlOrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class OrderVO {

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * zl_ordertype表ID
     */
    private Byte moldtype;

    /**
     * 父订单编号
     */
    private String parentorderserialno;

    /**
     * 订单编号
     */
    private String orderserialno;

    /**
     * 父订单ID
     */
    private Long parentorderid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 房间ID
     */
    private Integer roomid;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 第一张商品图片地址
     */
    private String goodscoverurl;

    /**
     * 备注信息
     */
    private String remark;

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
     * 配送方式0:快递,1自提,2酒店配送
     */
    private Byte deliverytype;

    /**
     * 配送地址(省市区合并完的详细地址), 不为空时表示土特产需要配送
     */
    private String deliveryaddress;

    /**
     * 来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
     */
    private Byte comeformid;

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
     * 退款类型:0无;1用户退款;2酒店退款
     */
    private Byte refundusertype;

    /**
     * 退款发起次数
     */
    private Byte refundcount;

    /**
     * 取消用户类型:0-无,1-用户,2-平台
     */
    private Byte cancelusertype;

    /**
     * 取消原因
     */
    private String cancelremark;

    /**
     * 用户优惠券id,zl_couponuser表id
     */
    private Long couponuserid;

    /**
     * 配送金额
     */
    private BigDecimal sendcash;

    /**
     * 操作员
     */
    private String operatorname;

    /**
     * 操作员IP
     */
    private String operatorip;

    /**
     * 操作员备注
     */
    private String operatorremark;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

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
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人联系电话
     */
    private String tel;

    /**
     * 支付时间
     */
    private Integer paydate;

    /**
     * 最新发起的退款时间
     */
    private Integer refunddate;

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
     * 订单类型：1酒店订单，2供应商订单
     */
    private Byte ordertype;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponcash;

    /**
     * 完成时间(已签收/已完成)
     */
    private Integer completedate;

    /**
     * 退款完成时间
     */
    private Integer refundfinishdate;

    /**
     * 是否父订单发起支付:0否;1是
     */
    private Boolean isparentpay;

    /**
     * 商品数量
     */
    private Long goodsTotal;

    /**
     * 该订单所包含的商品详情信息
     */
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
     * 是否寄件
     */
    private Boolean ismail = false;

}
