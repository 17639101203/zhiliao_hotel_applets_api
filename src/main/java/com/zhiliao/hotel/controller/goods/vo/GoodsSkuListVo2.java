package com.zhiliao.hotel.controller.goods.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-02 15:06
 **/
@Data
public class GoodsSkuListVo2 {

    /**
     * 酒店商品Id
     */
    private Integer goodsId;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Byte belongModule;

    /**
     * 商品图片
     */
    private String coverImgUrl;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品规格列表
     */
    private List<List<GoodsSkuVO>> GoodsSkuVOLists;

    /**
     * 商品属性列表
     */
    private List<Integer> categoryIdList;

}
