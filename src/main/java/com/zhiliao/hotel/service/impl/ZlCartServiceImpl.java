package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.mapper.ZlCartMapper;
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

    private final ZlCartMapper zlCartMapper;

    @Autowired
    public ZlCartServiceImpl(ZlCartMapper zlCartMapper) {
        this.zlCartMapper = zlCartMapper;
    }

    @Override
    public List<UserCartVo> findUserCart(Integer hotelId, Long userId) {
        return zlCartMapper.findUserCart(hotelId, userId);
    }

    @Override
    public void deleteUserCart(Integer hotelId, Long userId) {
        zlCartMapper.deleteUserCart(hotelId, userId);
    }

    @Override
    public void addUserCartBatch(Integer hotelId, Long userId, List<AddCartParam> addCartParams, Integer date) {
        zlCartMapper.addUserCartBatch(hotelId, userId, addCartParams, date);
    }
}