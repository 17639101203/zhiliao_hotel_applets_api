package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-22 17:30
 **/
@Data
public class GoodsShortInfoVO {

    /**
     * 用户id
     */
    private Long userID;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongModule;

    /**
     * 酒店商品表自增id
     */
    private Integer hotelGoodsSkuID;

    /**
     * 酒店HotelID
     */
    private Integer hotelID;
    
}
