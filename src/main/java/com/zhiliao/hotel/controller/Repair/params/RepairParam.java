package com.zhiliao.hotel.controller.Repair.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("报修下单接口传参")
@Data
@ToString
public class RepairParam {

    /**
     * 酒店ID
     */
    @NotNull(message = "酒店Id不能为空!")
    @ApiModelProperty(value = "酒店Id", required = true)
    private Integer hotelid;

    /**
     * 房间ID
     */
    @NotNull(message = "房间Id不能为空!")
    @ApiModelProperty(value = "房间Id", required = true)
    private Integer roomid;

    /**
     * 房间号
     */
    @NotBlank(message = "房间号不能为空!")
    @NotNull(message = "房间号不能为空!")
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    /**
     * 酒店名
     */
    @NotBlank(message = "酒店名不能为空!")
    @NotNull(message = "酒店名不能为空!")
    @ApiModelProperty(value = "酒店名", required = true)
    private String hotelname;

    /**
     * 报修信息
     */
    @ApiModelProperty(value = "报修信息", required = true)
    private String remark;

    /**
     *  报修图片地址,多个用 | 隔开
     */
    private String imgurls;

}
