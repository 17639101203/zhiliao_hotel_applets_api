package com.zhiliao.hotel.controller.myAppointment.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-24 17:16
 **/
@Data
public class ZlCleanOrderDTO implements Serializable {

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户电话
     */
    private String tel;

    /**
     * 订单编号
     */
    private String serialnumber;

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
     * 来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
     */
    private Integer comeformid;

    /**
     * 预定清扫时间
     */
    private Integer bookdate;

    /**
     * 超时时间
     */
    private Integer timeoutdate;

    /**
     * 其它需求备注
     */
    private String remark;

    /**
     * -1:已取消;0待清扫;1已完成;2已接单
     */
    private Byte orderstatus;

    /**
     * 状态信息
     */
    private String statusmessage;

    /**
     * 取消用户类型:1-用户,2-平台
     */
    private Byte cancelusertype;

    /**
     * 取消原因
     */
    private String cancelremark;

    /**
     * 操作人员
     */
    private String operatorname;

    /**
     * 操作人员IP
     */
    private String operatorip;

    /**
     * 操作人员备注
     */
    private String operatorremark;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 支付/取消时间
     */
    private Integer updatedate;

}
