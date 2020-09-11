package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.goods.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsService {

    List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule);

    PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName);

    GoodsSkuListVo2 findGoodsSkuList(Integer hotelId, Integer goodsId);

    GoodsListVo findGoodsDetail(Integer goodsId);

    List<GoodsListVo> findRecommendGoodsList(Integer hotelId);

    void updateGoodsCount(String out_trade_no);

    List<EsGoodsVO> searchGoods(Integer hotelId, String selectParam, Integer belongModule, Integer pageNo, Integer pageSize);

    BusinessHoursVO getBusinessHours(Integer menuId);

    GoodsPropertysVO findGoodsPropertyIdInfo(Integer hotelId, Integer goodsId, List<Integer> goodsPropertyIdList);

    void updateGoodsCountReturn(Long orderid);

    GoodsPropertysVO findSelectedGoodsProperty(Integer hotelGoodsSkuId);
}