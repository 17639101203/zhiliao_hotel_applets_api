package com.zhiliao.hotel.controller.serviceorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 客房服务订单详情vo
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@ApiModel("客房服务订单详情返回实体")
@Data
public class ServiceorderInfoVo implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long orderid;

    @ApiModelProperty(value = "订单编号")
    private String serialnumber;

    @ApiModelProperty(value = "房间号")
    private String roomnumber;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "下单时间")
    private String createdate;

    @ApiModelProperty(value = "送达时间")
    private String bookdate;

    @ApiModelProperty(value = "是否尽快送达，0：其他时间，1：尽快送达")
    private Integer isUrgent;

    @ApiModelProperty(value = "订单商品列表")
    private List<goods> orderGoodsList;

    @Data
    @NoArgsConstructor
    public static class goods implements Serializable {

        @ApiModelProperty(value = "商品id")
        private Integer goodsid;

        @ApiModelProperty(value = "商品名称")
        private String goodsname;

        @ApiModelProperty(value = "购买数量")
        private Integer goodscount;

        @ApiModelProperty(value = "商品图片")
        private String goodscoverurl;
    }

}
