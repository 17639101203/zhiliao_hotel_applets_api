package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.goods.vo.EsGoods;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuListVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsService {

    List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule);

    PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName);

    List<GoodsSkuListVo> findGoodsSkuList(Integer hotelId, Integer goodsId);

    GoodsListVo findGoodsDetail(Integer goodsId);

    void updateGoodsCount(String out_trade_no);

    List<EsGoods> searchGoods(Integer hotelId, String selectParam, Integer belongModule, Integer pageNo, Integer pageSize);
}