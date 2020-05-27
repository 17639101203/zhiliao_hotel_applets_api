package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuListVo;
import com.zhiliao.hotel.model.ZlHotelGoods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsMapper extends Mapper<ZlHotelGoods> {

    List<Map<String, String>> findGoodsCategory(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule);

    List<GoodsListVo> findGoodsList(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule, @Param("categoryName") String categoryName);

    List<GoodsSkuListVo> findGoodsSkuList(@Param("hotelId") Integer hotelId, @Param("goodsId") Integer goodsId);

    GoodsListVo findGoodsDetail(@Param("goodsId") Integer goodsId);

    void updateGoodsTotalVisitCount(@Param("goodsId") Integer goodsId);

    void updateGoods(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount);

    void updateGoodsSku(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount);

    void updateHotelGoodsSku(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount);

}