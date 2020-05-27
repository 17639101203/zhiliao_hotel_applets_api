package com.zhiliao.hotel.controller.serviceorder.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 客房服务订单提交dto
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@Data
@ApiModel("客房服务订单提交实体")
public class ServiceorderCommitParams implements Serializable {

    /**
     * 酒店ID
     */
    @ApiModelProperty(value = "酒店id", required = true)
    private Integer hotelid;

    /**
     * 酒店名
     */
    @ApiModelProperty(value = "酒店名称", required = true)
    private String hotelname;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间id")
    private Integer roomid;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号", required = true)
    private String roomnumber;

    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间(时间戳，精确到秒)", required = true)
    private Integer bookdate;

    /**
     * 是否尽快送达
     */
    @ApiModelProperty(value = "是否尽快送达，0：其他时间，1：尽快送达", required = true)
    private Integer isUrgent;

    /**
     * 订单商品
     */
    @ApiModelProperty(value = "订单商品", required = true)
    private List<orderGoods> orderGoods;

    /**
     * 其它需求备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @Data
    @NoArgsConstructor
    public static class orderGoods implements Serializable {

        @ApiModelProperty(value = "商品id", required = true)
        private Integer goodsId;

        @ApiModelProperty(value = "购买数量", required = true)
        private Integer goodsCount;
    }
}
