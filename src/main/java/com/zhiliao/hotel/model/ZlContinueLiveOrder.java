package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author null
 * @date 2020-06-06
 */
@Table(name = "zl_continueliveorder")
@Data
public class ZlContinueLiveOrder implements Serializable {
    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
     * 用户名
     */
    private String username;

    /**
     * 订单编号
     */
    private String orderserialno;

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
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 手机
     */
    private String tel;

    /**
     * 订单状态:-1:已取消;0待处理;1处理完成;2已接单
     */
    private Byte orderstatus;

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
     * 取消用户类型:1-用户,2-平台
     */
    private Byte cancelusertype;

    /**
     * 取消原因
     */
    private String cancelremark;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 续住时间
     */
    private Integer continuelivedate;

    /**
     * 退房时间
     */
    private Integer checkoutdate;

    /**
     * 创建时间（下单时间)
     */
    private Integer createdate;

    /**
     * 修改时间（操作时间）
     */
    private Integer updatedate;
}