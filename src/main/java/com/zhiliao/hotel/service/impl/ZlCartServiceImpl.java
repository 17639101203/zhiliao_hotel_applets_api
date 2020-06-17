package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.mapper.ZlCartMapper;
import com.zhiliao.hotel.service.ZlCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public void emptyUserCart(Integer hotelId, Long userId, Integer belongModule) {
        zlCartMapper.emptyUserCart(hotelId, userId, belongModule);
    }

    @Override
    public void addUserCartBatch(Integer hotelId, Long userId, List<AddCartParam> addCartParams, Integer date) {
        List<UserCartVo> userCartVoList = zlCartMapper.findUserCart(hotelId, userId);
        for (AddCartParam addCartParam : addCartParams) {
            for (UserCartVo userCartVo : userCartVoList) {
                if (userCartVo.getHotelGoodsSkuId().equals(addCartParam.getHotelGoodsSkuId())) {
                    addCartParam.setGoodsCount(userCartVo.getGoodsCount() + addCartParam.getGoodsCount());
                }
            }
        }
        // 先删除用户之前购物车数据
        zlCartMapper.emptyUserCart(hotelId, userId, 0);
        // 添加新的购物车数据
        zlCartMapper.addUserCartBatch(hotelId, userId, addCartParams, date);
    }

    @Override
    public Map<String, List<UserCartVo>> findUserCart(Integer hotelId, Long userId) {
        List<UserCartVo> userCartVoList = zlCartMapper.findUserCart(hotelId, userId);
        List<UserCartVo> userCartVoOneList = new LinkedList<>();
        List<UserCartVo> userCartVoTwoList = new LinkedList<>();
        List<UserCartVo> userCartVoThreeList = new LinkedList<>();
        List<UserCartVo> userCartVoFourList = new LinkedList<>();
        for (UserCartVo userCartVo : userCartVoList) {
            Integer belongModule = userCartVo.getBelongModule();
            if (belongModule == 1) {
                userCartVoOneList.add(userCartVo);
            }
            if (belongModule == 2) {
                userCartVoTwoList.add(userCartVo);
            }
            if (belongModule == 3) {
                userCartVoThreeList.add(userCartVo);
            }
            if (belongModule == 4) {
                userCartVoFourList.add(userCartVo);
            }
        }
        Map<String, List<UserCartVo>> map = new HashMap<>();
        map.put("1", userCartVoOneList);
        map.put("2", userCartVoTwoList);
        map.put("3", userCartVoThreeList);
        map.put("4", userCartVoFourList);
        return map;
    }
}