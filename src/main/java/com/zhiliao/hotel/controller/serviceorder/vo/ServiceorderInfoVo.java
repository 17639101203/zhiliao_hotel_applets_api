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

    @ApiModelProperty(value = "预约时间")
    private Integer bookdate;

    @ApiModelProperty(value = "下单时间")
    private Integer createdate;

    @ApiModelProperty(value = "送达时间，尽快送达值为0")
    private Integer deliverydate;

    @ApiModelProperty(value = "订单商品列表")
    private List<goods> orderGoodsList;

    @ApiModelProperty(value = "订单状态 -1:已取消;0待配送;1已完成")
    private Byte orderstatus;

    /**
     *  酒店ID
     */
    @ApiModelProperty(value = "酒店id")
    private Integer hotelid;

    /**
     * 状态信息
     */
    @ApiModelProperty(value = "状态信息")
    private String statusmessage;

    /**
     * 酒店名称
     */
    @ApiModelProperty(value = "酒店名称")
    private String hotelname;

    /**
     * 修改时间
     */
    private Integer updatedate;

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
