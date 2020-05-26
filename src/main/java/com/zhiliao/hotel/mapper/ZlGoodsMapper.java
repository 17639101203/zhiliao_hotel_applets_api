package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.model.ZlGoods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsMapper extends Mapper<ZlGoods> {

    List<Map<String, String>> findGoodsCategory(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule);

    List<GoodsListVo> findGoodsList(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule, @Param("categoryName") String categoryName);

    List<Map<String, Object>> findGoodsSkuList(@Param("goodsId") Integer goodsId);

    GoodsListVo findGoodsDetail(@Param("goodsID") Integer goodsID);

    void updateTotalVisitCount(@Param("goodsID") Integer goodsID, @Param("totalvisitcount") Integer totalvisitcount);

    void updateGoods(@Param("goodsID") Integer goodsID, @Param("goodsCount") Integer goodsCount);

    void updateGoodsSku(@Param("skuID") Integer skuID, @Param("goodsCount") Integer goodsCount);

    void updateHotelGoodsSku(@Param("skuID") Integer skuID, @Param("hotelID") Integer hotelID, @Param("goodsCount") Integer goodsCount);

    public List<GoodsListVo> getProductsFeaturedList();
}