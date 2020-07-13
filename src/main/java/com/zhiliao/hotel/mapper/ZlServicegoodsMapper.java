package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.servicegoods.vo.ServicegoodsListVo;
import com.zhiliao.hotel.model.ZlServicegoods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by xiegege on 2020/4/23.
 */

public interface ZlServicegoodsMapper extends Mapper<ZlServicegoods> {

    List<String> findServicegoodsCategory(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule);

    List<ServicegoodsListVo> findServicegoodsList(@Param("hotelId") Integer hotelId, @Param("belongModule") Integer belongModule, @Param("categoryName") String categoryName, @Param("keyword") String keyword);

    ServicegoodsListVo findServicegoodsDetail(@Param("goodsId") Integer goodsId);

    void updateServicegoodsVisitCount(@Param("goodsId") Integer goodsId);

    List<ZlServicegoods> findAllByHotelIdAndGoodsIds(@Param("hotelId") Integer hotelId, @Param("goodsIds") String goodsIds);

    List<ServicegoodsListVo> searchAllServicegoods(@Param("hotelId") Integer hotelId);
}