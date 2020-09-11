package com.zhiliao.hotel.controller.myAppointment.dto;

import com.zhiliao.hotel.model.ZlServiceorderdetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-24 17:55
 **/
@Data
public class ZlServiceorderResultDTO implements Serializable {

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
     * 服务项目
     */
    private String serviceitem;

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
     * 第一张商品图片地址
     */
    private String goodscoverurl;

    /**
     * 楼层号
     */
    private String floornumber;

    /**
     * 房间号
     */
    private String roomnumber;


    /**
     * 预约时间
     */
    private Integer bookdate;

    /**
     * 送达时间;默认0表示尽快送达
     */
    private Integer deliverydate;

    /**
     * 超时时间
     */
    private Integer timeoutdate;

    /**
     * 其它需求备注
     */
    private String remark;

    /**
     * -1:已取消;0待配送;1已处理
     */
    private Byte orderstatus;

    /**
     * 状态信息
     */
    private String statusmessage;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 订单详情表的封装
     *
     */
    private List<ZlServiceorderdetail> serviceorderdetails;

}
