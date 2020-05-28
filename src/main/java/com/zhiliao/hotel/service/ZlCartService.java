package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;

import java.util.List;

/**
 * Created by xiegege on 2020/4/14.
 */

public interface ZlCartService {

    void emptyUserCart(Integer hotelId, Long userId, Integer belongModule);

    void addUserCartBatch(Integer hotelId, Long userId, List<AddCartParam> addCartParams, Integer date);

    List<UserCartVo> findUserCart(Integer hotelId, Long userId);
}
