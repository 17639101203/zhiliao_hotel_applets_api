package com.zhiliao.hotel.controller.hotelfacility.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 17:21
 **/
@Data
@ApiModel("酒店设施下单接口传参")
public class HotelFacilityOrderParam {

    @ApiModelProperty(value = "设施ID", required = true)
    private Integer facilityId;

    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelId;

    @ApiModelProperty(value = "酒店名", required = true)
    private String hotelName;

    @ApiModelProperty(value = "房间ID", required = true)
    private Integer roomid;

    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    private String Tel;

    @ApiModelProperty(value = "设施名称", required = true)
    private String facilityName;

    @ApiModelProperty(value = "设施封面图片地址", required = true)
    private String coverUrl;

    @ApiModelProperty(value = "备注信息", required = false)
    private String remark;

    @ApiModelProperty(value = "实际支付金额", required = true)
    private BigDecimal actuallyPay;

    @ApiModelProperty(value = "开始时间", required = true)
    private Integer usebegindate;

    @ApiModelProperty(value = "结束时间", required = true)
    private Integer useenddate;
}
