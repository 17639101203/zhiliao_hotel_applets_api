package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-06-05
 */
@Table(name = "zl_wakeorder")
@Data
public class ZlWakeOrder implements Serializable {
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
     * 订单编号
     */
    private String orderserialno;

    /**
     * zl_ordertype表ID
     */
    private Byte moldtype;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 楼层数
     */
    private String floornumber;

    /**
     * 房间ID
     */
    private Integer roomid;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机
     */
    private String tel;

    /**
     * 订单状态:-1:已取消;0待处理;1处理完成
     */
    private Byte orderstatus;

    /**
     * 取消用户类型:1-用户,2-平台
     */
    private Byte cancelusertype;

    /**
     * 取消原因
     */
    private String cancelremark;

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
     * 叫醒时间
     */
    private Integer wakedate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 创建时间（下单时间)
     */
    private Integer createdate;

    /**
     * 修改时间（操作时间）
     */
    private Integer updatedate;

}