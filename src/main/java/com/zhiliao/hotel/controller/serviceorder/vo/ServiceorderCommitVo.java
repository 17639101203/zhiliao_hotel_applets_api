package com.zhiliao.hotel.controller.serviceorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 客房服务订单提交vo
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@ApiModel("客房服务订单提交返回实体")
public class ServiceorderCommitVo implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long orderid;

    @ApiModelProperty(value = "酒店服务平均用时")
    private Integer dealWithTime;

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getDealWithTime() {
        return dealWithTime;
    }

    public void setDealWithTime(Integer dealWithTime) {
        this.dealWithTime = dealWithTime;
    }
}
