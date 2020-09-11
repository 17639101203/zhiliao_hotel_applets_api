package com.zhiliao.hotel.controller.myAppointment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-24 20:00
 **/
@Data
public class HotelFacilityOrderParamDTO {

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单id")
    private Long orderid;

    /**
     * 设施ID
     */
    @ApiModelProperty(value = "设施ID")
    private Integer facilityid;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userid;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String serialnumber;

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店ID")
    private Integer hotelid;

    /**
     * 酒店名
     */
    @ApiModelProperty(value = "酒店名")
    private String hotelname;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间ID")
    private Integer roomid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String roomnumber;

    /**
     * 设施名称
     */
    @ApiModelProperty(value = "设施名称")
    private String facilityname;

    /**
     * 设施封面图片地址
     */
    @ApiModelProperty(value = "设施封面图片地址")
    private String coverurl;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 收费方式:1-按次,2-按小时
     */
    private Byte chargemethod;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 总价
     */
    @ApiModelProperty(value = "总价")
    private BigDecimal totalprice;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal actuallypay;

    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它
     */
    @ApiModelProperty(value = "支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它")
    private Byte paytype;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    @ApiModelProperty(value = "来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店")
    private Integer comeformid;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Integer usebegindate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Integer useenddate;

    /**
     * 可取消预约时间
     */
    @ApiModelProperty(value = "可取消预约时间")
    private Integer cancancelorderminute;

    /**
     * 0无须支付;1:待支付;2已支付
     */
    @ApiModelProperty(value = "0无须支付;1:待支付;2已支付")
    private Byte paystatus;

    /**
     * -1:已取消;0等待确认;1已确认
     */
    @ApiModelProperty(value = "-1:已取消;0等待确认;1已确认")
    private Byte orderstatus;

    /**
     * 状态信息
     */
    private String statusmessage;

    /**
     * 后台操作人
     */
    @ApiModelProperty(value = "后台操作人")
    private String operatorname;

    /**
     * 每次使用分钟数
     */
    private Integer onceuseminute;

    /**
     * 操作人IP
     */
    @ApiModelProperty(value = "操作人IP")
    private String operatorip;

    /**
     * 操作人备注
     */
    @ApiModelProperty(value = "操作人备注")
    private String operatorremark;

    /**
     * 删除状态:0正常;1删除;
     */
    @ApiModelProperty(value = "删除状态:0正常;1删除;")
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    @ApiModelProperty(value = "用户删除:0否;1是")
    private Boolean isuserdelete;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Integer createdate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Integer updatedate;

    /**
     * 预定金额
     */
    private BigDecimal bookmoney;

}
