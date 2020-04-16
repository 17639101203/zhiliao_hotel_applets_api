package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsService {

    List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule);

    Map<String, Object> findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName);

    /**
     * 根据商品id查询详情数据
     *
     * @param goodsID
     * @return
     */
    GoodsListVo findGoodsDetail(Integer goodsID);

}
