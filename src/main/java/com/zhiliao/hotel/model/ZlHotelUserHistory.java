package com.zhiliao.hotel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "zl_hoteluserhistory")
@ApiModel(value = "用户酒店历史")
@Data
public class ZlHotelUserHistory implements Serializable {

    @ApiModelProperty(value = "自增id")
    private Long recid;

    @ApiModelProperty(value = "用户id")
    private Long userid;

    @ApiModelProperty(value = "酒店id")
    private Integer hotelid;

    @ApiModelProperty(value = "酒店名称")
    private String hotelname;

    @ApiModelProperty(value = "房间id")
    private Integer roomid;

    @ApiModelProperty(value = "客房编号")
    private String roomnumber;

    @ApiModelProperty(value = "入住日期")
    private Integer checkindate;

    @ApiModelProperty(value = "用户删除:0否;1是")
    private Boolean isuserdelete;

    @ApiModelProperty(value = "删除状态:0正常;1删除;")
    private Boolean isdelete;

    @ApiModelProperty(value = "添加日期")
    private Integer createdate;

}

