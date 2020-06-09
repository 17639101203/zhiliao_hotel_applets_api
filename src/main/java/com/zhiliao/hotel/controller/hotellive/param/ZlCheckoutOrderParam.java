package com.zhiliao.hotel.controller.hotellive.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:35
 **/
@ApiModel("退房订单")
@Data
public class ZlCheckoutOrderParam {

    /**
     * 退房时间
     */
    @ApiModelProperty(value = "退房时间", required = true)
    private Long CheckOutDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
