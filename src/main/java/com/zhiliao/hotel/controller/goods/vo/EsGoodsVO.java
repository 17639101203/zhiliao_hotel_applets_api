package com.zhiliao.hotel.controller.goods.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-15 14:56
 **/
@Data
public class EsGoodsVO {

    /**
     * 商品id
     */
    private Integer goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品图片
     */
    private String coverimgurl;
    /**
     * 原价
     */
    private Double originalprice;
    /**
     * 现价
     */
    private Double currentprice;
    /**
     * 商品分类
     */
    private Integer goodscategoryid;
    /**
     * 所属模块
     */
    private Integer belongmodule;
    /**
     * 酒店id
     */
    private Integer hotelid;
    /**
     * 是否多规格
     */
    private Boolean isManySku;

    /**
     * 库存
     */
    private Integer stockCount;

    /**
     * -1:紧急下架;0:安全下架;1上架;2导入待编辑
     */
    private Integer goodsStatus;
}
