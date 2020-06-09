package com.zhiliao.hotel.controller.hotellive.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:35
 **/
@ApiModel("退房订单")
public class ZlCheckoutOrderParam {

    /**
     * 退房时间
     */
    @ApiModelProperty(value = "退房时间", required = true)
    private Integer CheckOutDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public Integer getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(Integer checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
