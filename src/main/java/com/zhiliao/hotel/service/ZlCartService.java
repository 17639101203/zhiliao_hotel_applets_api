package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.model.ZlCart;

import java.util.List;

/**
 * Created by xiegege on 2020/4/14.
 */

public interface ZlCartService {

    ZlCart findCartDoesItExist(Long userId, Integer hotelId, Integer goodsId, Integer skuId);

    void addCart(ZlCart cart);

    void deleteCartByGoodsCountZero(ZlCart cart);

    void updateCartGoodsCount(ZlCart cart);

    List<UserCartVo> findUserCart(Integer hotelId, Long userId, Integer belongModule);

    void emptyCart(Integer hotelId, Long userId, Integer belongModule);
}
