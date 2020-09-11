package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款申请记录
 *
 * @author null
 * @date 2020-08-14
 */
@Data
@Table(name = "zl_refundrecord")
public class ZlRefundRecord implements Serializable {

    /**
     * 申请记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer recid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 退款单号
     */
    private String refundserialno;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户联系手机
     */
    private String usertel;

    /**
     * 用户申请退款备注
     */
    private String userremark;

    /**
     * 退货商品图片
     */
    private String refundgoodsurls;

    /**
     * 退款金额
     */
    private BigDecimal refundmoney;

    /**
     * 售后类型：1退货退款,2仅退款
     */
    private Byte refundtype;

    /**
     * 退款状态:-12退款关闭(不可再申述);-2退款被驳回;-1用户取消退款;1审核中;2审核通过;3退款中(退款发起);4已退款
     */
    private Byte recordstatus;

    /**
     * 用户已发货：1已发货;0未发货
     */
    private Boolean isusersend;

    /**
     * 回寄快递ID
     */
    private Short userexpressid;

    /**
     * 回寄物流编号
     */
    private String usertracknumber;

    /**
     * 用户发货时间
     */
    private Integer usersenddate;

    /**
     * 审核类型:1-B端(供应商/酒店);2-S端(平台审核)
     */
    private Byte checktype;

    /**
     * 是否已进入申述0否1是
     */
    private Boolean isrepresent;

    /**
     * 0正常;1删除
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 订单商品状态 1未收到货 2已收到货
     */
    private Byte goodsstatus;

}