package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-21 11:34
 **/
@Data
public class GoodsStockCountNo {

    /**
     * 商品goodsID
     */
    private Integer goodsID;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 酒店商品表自增id
     */
    private Integer hotelGoodsSkuID;

}
