package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlCart;

/**
 * Created by xiegege on 2020/4/14.
 */

public interface ZlCartService {

    ZlCart findCartByUserIdAndHotelIdAndSkuId(Long userId, Integer hotelId, Integer skuId);

    void addCart(ZlCart cart);

    void deleteCartByGoodsCountZero(ZlCart cart);

    void updateCartGoodsCount(ZlCart cart);
}
