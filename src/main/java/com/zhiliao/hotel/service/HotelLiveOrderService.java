package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.hotellive.param.ZlCheckoutOrderParam;
import com.zhiliao.hotel.controller.hotellive.param.ZlContinueLiveOrderParam;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.model.ZlCheckoutOrder;
import com.zhiliao.hotel.model.ZlContinueLiveOrder;

import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:37
 **/
public interface HotelLiveOrderService {

    Map<String,Object> checkoutOrder(Long userID, HotelBasicVO hotelBasicVO, ZlCheckoutOrderParam zlCheckoutOrderParam);

    void continueLiveOrder(Long userID, HotelBasicVO hotelBasicVO, ZlContinueLiveOrderParam zlContinueLiveOrderParam);

    void cancelCheckoutOrder(Long orderID);

    void cancelContinueLiveOrder(Long orderID);

    void userDeleteCheckoutOrder(Long orderID);

    void userDeleteContinueLiveOrder(Long orderID);

    ZlCheckoutOrder checkoutOrderDetail(Long orderID);

    ZlContinueLiveOrder continueLiveOrderDetail(Long orderID);
}
