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
@Table(name = "zl_checkoutorder")
@Data
public class ZlCheckoutOrder implements Serializable {
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
     * 订单状态:-1:取消退房;0等待退房;1完成退房
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
     * 退房时间
     */
    private Integer checkoutdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间（下单时间)
     */
    private Integer createdate;

    /**
     * 修改时间（操作时间）
     */
    private Integer updatedate;
}