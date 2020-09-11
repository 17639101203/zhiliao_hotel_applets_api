package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsPropertyInfoVO;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuVO;
import com.zhiliao.hotel.model.ZlHotelgoodssku;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

public interface ZlGoodsMapper extends Mapper<ZlHotelgoodssku> {

    List<Map<String, String>> findGoodsCategory(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule);

    List<GoodsListVo> findGoodsList(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule, @Param("categoryName") String categoryName);

    List<GoodsSkuListVo> findGoodsSkuList(@Param("hotelId") Integer hotelId, @Param("goodsId") Integer goodsId);

    GoodsListVo findGoodsDetail(@Param("goodsId") Integer goodsId);

    void updateGoodsTotalVisitCount(@Param("goodsId") Integer goodsId);

    List<GoodsListVo> findRecommendGoodsList(@Param("hotelId") Integer hotelId);

    void updateGoods(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    void updateGoodsSku(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    void updateHotelGoodsSku(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    Integer getStockCount(@Param("hotelID") Integer hotelID, @Param("hotelGoodsSkuID") Integer hotelGoodsSkuID);

    Integer findSkuCount(@Param("goodsId") Integer goodsId);

    Integer getByHotelIDAndGoodsID(@Param("hotelid") Integer hotelid, @Param("goodsid") Integer goodsid);

    GoodsSkuVO findGoodsSku(@Param("propertyId") String propertyId);

    List<GoodsPropertyInfoVO> findGoodsPropertyIdInfo(@Param("hotelId") Integer hotelId, @Param("goodsId") Integer goodsId);

    void updateGoodsReturn(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    void updateGoodsSkuReturn(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    void updateHotelGoodsSkuReturn(@Param("hotelGoodsSkuID") Integer hotelGoodsSkuID, @Param("goodsCount") Integer goodsCount, @Param("currertTime") Integer currertTime);

    GoodsPropertyInfoVO findSelectedGoodsProperty(@Param("hotelGoodsSkuId") Integer hotelGoodsSkuId);
}