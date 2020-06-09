package com.zhiliao.hotel.controller.rentcar.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 17:05
 **/
@Data
@ApiModel("租车下单接口传参")
public class RentCarOrderParam {

    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelId;

    @ApiModelProperty(value = "酒店名称", required = true)
    private String hotelName;

    @ApiModelProperty(value = "房间Id", required = true)
    private Integer roomId;

    @ApiModelProperty(value = "房间号", required = true)
    private String roomNumber;

    @ApiModelProperty(value = "租车车型号", required = true)
    private String goodsName;

    @ApiModelProperty(value = "车牌号", required = true)
    private String carNumber;

    @ApiModelProperty(value = "租车开始时间", required = true)
    private Integer rentBeginDate;

    @ApiModelProperty(value = "租车结束时间", required = true)
    private Integer rentEndDate;

    @ApiModelProperty(value = "租金/天", required = true)
    private BigDecimal rentPrice;

    @ApiModelProperty(value = "租车费用", required = true)
    private BigDecimal rentTotalPrice;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    private String Tel;
}
