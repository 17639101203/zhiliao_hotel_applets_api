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
    @ApiModelProperty(value = "用户优惠券自增ID", required = true)
    private Integer recID;

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
     * 配送地址
     */
    @ApiModelProperty(value = "配送地址")
    private String deliveryAddress;

    /**
     * 送达时间
     */
    @ApiModelProperty(value = "送达时间", required = true)
    private Long deliveryDate;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderSerialNo;

}
