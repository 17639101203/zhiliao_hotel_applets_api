package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.mapper.ZlCartMapper;
import com.zhiliao.hotel.model.ZlCart;
import com.zhiliao.hotel.service.ZlCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiegege on 2020/4/14.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCartServiceImpl implements ZlCartService {

    private ZlCartMapper zlCartMapper;

    @Autowired
    public ZlCartServiceImpl(ZlCartMapper zlCartMapper) {
        this.zlCartMapper = zlCartMapper;
    }

    @Override
    public ZlCart findCartDoesItExist(Long userId, Integer hotelId, Integer goodsId, Integer skuId) {
        ZlCart cart = new ZlCart();
        cart.setUserid(userId);
        cart.setHotelid(hotelId);
        cart.setGoodsid(goodsId);
        cart.setSkuid(skuId);
        return zlCartMapper.selectOne(cart);
    }

    @Override
    public void addCart(ZlCart cart) {
        zlCartMapper.insert(cart);
    }

    @Override
    public void deleteCartByGoodsCountZero(ZlCart cart) {
        zlCartMapper.deleteByPrimaryKey(cart);
    }

    @Override
    public void updateCartGoodsCount(ZlCart cart) {
        zlCartMapper.updateCartGoodsCount(cart.getCartid(), cart.getGoodscount(), cart.getUpdatedate());
    }

    @Override
    public List<UserCartVo> findUserCart(Integer hotelId, Long userId, Integer belongModule) {
        return zlCartMapper.findUserCart(hotelId, userId, belongModule);
    }

    @Override
    public void emptyCart(Integer hotelId, Long userId, Integer belongModule) {
        zlCartMapper.emptyCart(hotelId, userId, belongModule);
    }
}