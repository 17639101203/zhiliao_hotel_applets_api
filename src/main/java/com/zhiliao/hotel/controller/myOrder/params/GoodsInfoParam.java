package com.zhiliao.hotel.controller.myOrder.params;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-08 11:20
 **/

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息VO
 */
@ApiModel("提交订单")
@Data
public class GoodsInfoParam {

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String goodsName;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    @ApiModelProperty(value = "所属模块：1:便利店;2餐饮服务;3情趣用品;4土特产", allowableValues = "1,2,3,4")
    private Short belongModule;

    /**
     * 商品封面图片
     */
    @ApiModelProperty(value = "商品封面图片", required = true)
    private String coverImgUrl;

    /**
     * 用户优惠券自增ID
     */
    @ApiModelProperty(value = "用户优惠券自增ID,不使用优惠券填-1", required = true)
    private Long couponUserId;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价", required = true)
    private BigDecimal price;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    private Integer goodsCount;

    /**
     * 酒店商品表自增id
     */
    @ApiModelProperty(value = "酒店商品表自增id", required = true)
    private Integer hotelGoodsSkuId;

    /**
     * 配送方式0:快递,1自提
     */
    @ApiModelProperty(value = "配送方式0:快递,1自提,2酒店配送", required = true)
    private Byte deliverytype;

    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private String consignee;

    /**
     * 收货人联系电话
     */
    @ApiModelProperty(value = "收货人联系电话")
    private String tel;

    /**
     * 配送地址
     */
    @ApiModelProperty(value = "配送地址")
    private String deliveryAddress;

    /**
     * 送达时间
     */
    @ApiModelProperty(value = "送达时间;默认0表示尽快送达", required = true)
    private Long deliveryDate;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderSerialNo;

    /**
     * 父订单编号
     */
    @ApiModelProperty(value = "父订单编号")
    private String parentOrderSerialNo;

}
