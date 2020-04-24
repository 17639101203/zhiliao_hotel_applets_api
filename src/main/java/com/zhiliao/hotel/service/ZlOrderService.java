package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.order.vo.GoodsInfoVO;
import com.zhiliao.hotel.controller.order.vo.HotelBasicVO;
import com.zhiliao.hotel.model.ZlOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZlOrderService {

    PageInfoResult findAllOrder(Long userID, Integer orderType, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize);

    void byOrderId(Long orderID);

    List<ZlOrder> submitOrder(Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoVO>> goodsInfoMap);

}
