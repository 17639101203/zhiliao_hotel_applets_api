package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.myOrder.vo.*;

import java.util.List;
import java.util.Map;

public interface ZlOrderService {

    PageInfoResult findAllOrder(OrderInfoVO vo);

    void byOrderId(Long orderID);

    UserGoodsReturn submitOrder(Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoVO>> goodsInfoMap);

    void updateOrder(String out_trade_no);

    List<OrderDetailVO> getOrderDetail(String out_trade_no);
}
