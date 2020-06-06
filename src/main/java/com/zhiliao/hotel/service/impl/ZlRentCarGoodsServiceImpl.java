package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlRentCarGoodsMapper;
import com.zhiliao.hotel.model.ZlRentCarGoods;
import com.zhiliao.hotel.service.ZlRentCarGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 09:37
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlRentCarGoodsServiceImpl implements ZlRentCarGoodsService {


    @Autowired
    private ZlRentCarGoodsMapper rentCarGoodsMapper;



    /**
     * 获取车型列表
     * @param hotelid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult carGoodsList(Integer hotelid, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlRentCarGoods> result = rentCarGoodsMapper.carGoodsList(hotelid);
        PageInfo<ZlRentCarGoods> pageInfo = new PageInfo<>(result);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 车型商品详情
     * @param goodsid
     * @return
     */
    @Override
    public ZlRentCarGoods rentCarDetail(Integer goodsid) {
        return rentCarGoodsMapper.rentCarDetail(goodsid);
    }
}
