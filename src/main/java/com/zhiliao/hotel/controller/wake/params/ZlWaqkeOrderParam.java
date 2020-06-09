package com.zhiliao.hotel.controller.wake.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 16:53
 **/
@Data
@ApiModel("叫醒下单接口传参")
public class ZlWaqkeOrderParam {

    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelId;

    @ApiModelProperty(value = "酒店名称", required = true)
    private String hotelName;

    @ApiModelProperty(value = "房间Id", required = true)
    private Integer roomId;

    @ApiModelProperty(value = "房间号", required = true)
    private String roomNumber;

    @ApiModelProperty(value = "叫醒时间,日期时间戳, 秒级别", required = true)
    private Integer wakeDate;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    private String Tel;
}
