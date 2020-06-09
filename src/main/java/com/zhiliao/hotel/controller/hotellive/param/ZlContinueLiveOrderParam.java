package com.zhiliao.hotel.controller.hotellive.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 16:21
 **/
@ApiModel("续住订单")
@Data
public class ZlContinueLiveOrderParam {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String tel;

    /**
     * 退房时间
     */
    @ApiModelProperty(value = "退房时间", required = true)
    private Long CheckOutDate;

    /**
     * 续住时间
     */
    @ApiModelProperty(value = "续住时间", required = true)
    private Long ContinueLiveDate;

}
