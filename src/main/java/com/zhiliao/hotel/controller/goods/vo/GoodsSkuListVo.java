package com.zhiliao.hotel.controller.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiegege
 * @date 2020/5/27 15:15
 */
@ApiModel("酒店商品规格")
@Data
public class GoodsSkuListVo {

    @ApiModelProperty(value = "酒店商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "规格分类Id")
    private String propertyId;

    @ApiModelProperty(value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产")
    private Byte belongModule;

    @ApiModelProperty(value = "商品图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

//    @ApiModelProperty(value = "商品规格列表")
//    private Map<String, List<GoodsSkuVO>> propertyMap;

    @ApiModelProperty(value = "商品规格列表")
    private List<List<GoodsSkuVO>> GoodsSkuVOLists;

}