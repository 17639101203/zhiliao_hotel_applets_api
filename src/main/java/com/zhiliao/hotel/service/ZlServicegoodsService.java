package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.servicegoods.vo.ServicegoodsListVo;

import java.util.List;

/**
 * Created by xiegege on 2020/4/23.
 */

public interface ZlServicegoodsService {

    List<String> findServicegoodsCategory(Integer hotelId, Integer belongModule);

    PageInfoResult findServicegoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName, String keyword);

    ServicegoodsListVo findServicegoodsDetail(Integer goodsId);

    PageInfoResult searchAllServicegoods(Integer hotelId, Integer pageNo, Integer pageSize);
}