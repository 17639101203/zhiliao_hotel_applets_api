package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.mapper.ZlGoodsMapper;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {


    private final ZlGoodsMapper zlGoodsMapper;

    @Autowired
    public ZlGoodsServiceImpl(ZlGoodsMapper zlGoodsMapper) {
        this.zlGoodsMapper = zlGoodsMapper;
    }

    @Override
    public List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule) {
        return zlGoodsMapper.findGoodsCategory(hotelId, belongModule);
    }

    @Override
    public PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<GoodsListVo> dataList = zlGoodsMapper.findGoodsList(hotelId, belongModule, categoryName);
        PageInfo<GoodsListVo> pageInfo = new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public List<Map<String, Object>> findGoodsSkuList(Integer goodsId) {
        return zlGoodsMapper.findGoodsSkuList(goodsId);
    }

    @Override
    public GoodsListVo findGoodsDetail(Integer goodsID) {
        GoodsListVo goodsListVo = zlGoodsMapper.findGoodsDetail(goodsID);
        if (goodsListVo != null) {
            zlGoodsMapper.updateTotalVisitCount(goodsID, goodsListVo.getTotalVisitCount());
        }
        return goodsListVo;
    }

    @Override
    public void updateGoodsCount(List<ZlOrderDetail> zlOrderDetailList) {
        for (ZlOrderDetail zlOrderDetail : zlOrderDetailList) {
            Integer goodsID = zlOrderDetail.getGoodsID();
            Integer goodsCount = zlOrderDetail.getGoodsCount();
            zlGoodsMapper.updateGoods(goodsID, goodsCount);
            zlGoodsMapper.updateGoodsSku(goodsID, goodsCount);
            zlGoodsMapper.updateHotelGoods(goodsID, goodsCount);
        }
    }
}