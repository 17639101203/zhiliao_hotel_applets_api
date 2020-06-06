package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRentCarGoods;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 09:42
 **/
public interface ZlRentCarGoodsMapper extends Mapper<ZlRentCarGoods> {
    List<ZlRentCarGoods> carGoodsList(Integer hotelid);

    ZlRentCarGoods rentCarDetail(Integer goodsid);
}
