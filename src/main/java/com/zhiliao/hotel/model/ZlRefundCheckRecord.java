package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 退款申请处理记录
 *
 * @author null
 * @date 2020-08-14
 */
@Data
@Table(name = "zl_refundcheckrecord")
public class ZlRefundCheckRecord implements Serializable {
    /**
     * 申请审核ID
     */
    private Integer checkrecid;

    /**
     * 申请记录ID
     */
    private Integer refundrecid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 审核类型:1-B端(供应商/酒店);2-S端(平台审核)
     */
    private Byte checktype;

    /**
     * 当前发起/处理类型:1-B端(供应商/酒店);2-S端(平台);3-用户
     */
    private Byte currentchecktype;

    /**
     * 退款状态:-12退款关闭(不可再申述);-2退款被驳回;-1用户取消退款;1审核中;2审核通过;3退款中(退款发起);4已退款
     */
    private Byte checkstatus;

    /**
     * 审核人员
     */
    private String operatorname;

    /**
     * 审核人员IP
     */
    private String operatorip;

    /**
     * 审核人员备注
     */
    private String operatorremark;

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
     * 用户操作类型-2用户取消申述,-1用户取消申请,0非用户操作,1用户发起申请,2用户发起申述,3用户上传物流信息
     */
    private Byte useroperatype;

}