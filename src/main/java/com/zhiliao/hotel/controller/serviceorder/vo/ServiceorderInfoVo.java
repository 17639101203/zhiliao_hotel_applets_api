package com.zhiliao.hotel.controller.serviceorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客房服务订单详情vo
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@ApiModel("客房服务订单详情返回实体")
@Data
public class ServiceorderInfoVo implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long orderid;

    @ApiModelProperty(value = "订单编号")
    private String serialnumber;

    @ApiModelProperty(value = "房间号")
    private String roomnumber;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "下单时间")
    private String createdate;




}
