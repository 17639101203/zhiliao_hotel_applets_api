package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 预定订单操作表
 *
 * @author null
 * @date 2020-09-03
 */
@Data
@Table(name = "zl_reservelog")
public class ZlReservelog implements Serializable {
    /**
     * 主键ID
     */
    private Long reserveid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 下单时间
     */
    private Integer booktime;

    /**
     * 操作时间
     */
    private Integer operatetime;

    /**
     * 超时时间(单位:分钟)
     */
    private Short losetime;

    /**
     * 使用时间(单位:秒)
     */
    private Integer usetime;

    /**
     * 操作状态-1:取消;1完成;2接单
     */
    private Boolean operatetype;

    /**
     * 是否超时
     */
    private Boolean islose;

    /**
     * 订单表
     */
    private Byte ordertype;

}