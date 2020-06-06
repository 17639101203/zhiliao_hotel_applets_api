package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlRentCarGoods;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 09:29
 **/
public interface ZlRentCarGoodsService {
    PageInfoResult carGoodsList(Integer hotelid, Integer pageNo, Integer pageSize);

    ZlRentCarGoods rentCarDetail(Integer goodsid);
}
