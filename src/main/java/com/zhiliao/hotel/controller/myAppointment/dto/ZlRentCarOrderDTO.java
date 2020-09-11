package com.zhiliao.hotel.controller.myAppointment.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-24 18:10
 **/
@Data
public class ZlRentCarOrderDTO implements Serializable {

    /**
     * 订单ID
     */
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
     * 订单状态:-1:已取消;0待处理;1处理完成;2已接单
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
     * 租车车型号
     */
    private String goodsname;

    /**
     * 车牌号
     */
    private String carnumber;

    /**
     * 租车开始时间
     */
    private Integer rentbegindate;

    /**
     * 租车结束时间
     */
    private Integer rentenddate;

    /**
     * 归还时间
     */
    private Integer givebackdate;

    /**
     * 租金/天
     */
    private BigDecimal rentprice;

    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 租车费用
     */
    private BigDecimal renttotalprice;

    /**
     * 可取消订单时间(分钟)
     */
    private Integer cancancelorderminute;

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

    /**
     * 商品图片
     */
    private String coverimgurl;

}
