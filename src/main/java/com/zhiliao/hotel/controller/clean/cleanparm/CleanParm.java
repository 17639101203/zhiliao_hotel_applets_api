package com.zhiliao.hotel.controller.clean.cleanparm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel("清扫下单请求参数")
public class CleanParm {


    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店ID", required = true)
    private Integer hotelid;

    /**
     * 酒店名
     */
    @ApiModelProperty(value = "酒店名", required = true)
    private String hotelname;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    /**
     * 预定清扫时间
     */
    @ApiModelProperty(value = "预定清扫时间", required = true)
    private Integer bookdate;

    /**
     * 其它需求备注
     */
    @ApiModelProperty(value = "其它需求备注", required = true)
    private String remark;


    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间ID", required = true)
    private Integer roomid;
}
