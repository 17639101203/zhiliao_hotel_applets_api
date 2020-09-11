package com.zhiliao.hotel.controller.myOrder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-15 16:58
 **/
@Data
@ApiModel("用户上传物流DTO")
public class UploadExpressInfoDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单Id")
    private Long orderid;

    /**
     * 快递Id
     */
    @NotNull(message = "快递Id不能为空")
    @ApiModelProperty(value = "快递Id")
    private Short expressid;

    /**
     * 回寄物流编号
     */
    @NotNull(message = "物流编号不能为空")
    @NotBlank(message = "物流编号不能为空")
    @ApiModelProperty(value = "回寄物流编号")
    private String usertracknumber;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String operatorremark;

}
