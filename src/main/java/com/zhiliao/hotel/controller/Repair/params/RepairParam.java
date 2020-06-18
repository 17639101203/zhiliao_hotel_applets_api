package com.zhiliao.hotel.controller.Repair.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("报修下单接口传参")
@Data
@ToString
public class RepairParam {

    /**
     * 报修订单id
     */
    @ApiModelProperty(value = "报修订单id", required = true)
    private Long orderid;

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelid;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间ID", required = true)
    private Integer roomid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;


    /**
     * 酒店名
     */
    @ApiModelProperty(value = "酒店名", required = true)
    private String hotelname;



    /**
     * 报修信息
     */
    @ApiModelProperty(value = "报修信息", required = true)
    private String remark;

}
