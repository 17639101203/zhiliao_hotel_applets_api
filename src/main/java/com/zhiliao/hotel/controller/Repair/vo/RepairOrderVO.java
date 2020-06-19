package com.zhiliao.hotel.controller.Repair.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-19 09:42
 **/
@Data
public class RepairOrderVO {

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 订单编号
     */
    private String serialnumber;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 报修图片地址,多个用 | 隔开
     */
    private String imgurls;

    /**
     * 报修图片列表
     */
    private List<String> imageurllist;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * -1:已取消;0待维修;1已完成
     */
    private Byte orderstatus;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 支付/取消时间
     */
    private Integer updatedate;

}
