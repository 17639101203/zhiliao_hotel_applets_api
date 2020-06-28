package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-28 15:42
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class DeleteOrderServiceImpl implements DeleteOrderService {

    @Autowired
    private ZlCleanOrderService zlCleanOrderService;

    @Autowired
    private ZlInvoiceService zlInvoiceService;

    @Autowired
    private ZlRepairService zlRepairService;

    @Autowired
    private ZlHotelFacilityOrderService zlHotelFacilityOrderService;

    @Autowired
    private ZlServiceorderService zlServiceorderService;

    @Autowired
    private ZlWakeOrderService wakeOrderService;

    @Autowired
    private ZlRentCarGoodsService rentCarGoodsService;

    @Autowired
    private HotelLiveOrderService hotelLiveOrderService;

    @Override
    public void deleteOrder(Long orderid, Integer orderServiceType) {
        if (orderServiceType == 1) {
            zlCleanOrderService.deleteCleanOrder(orderid);
        } else if (orderServiceType == 2) {
            zlInvoiceService.deleteInvoiceOrder(orderid);
        } else if (orderServiceType == 3) {
            zlRepairService.userDeleteRepairOrder(orderid);
        } else if (orderServiceType == 4) {
            zlHotelFacilityOrderService.userDeleteFacilityOrder(orderid);
        } else if (orderServiceType == 5) {
            zlServiceorderService.userDeleteServiceOrder(orderid);
        } else if (orderServiceType == 6) {
            wakeOrderService.dlWakeOrder(orderid);
        } else if (orderServiceType == 7) {
            rentCarGoodsService.dlRentCarOrder(orderid);
        } else if (orderServiceType == 8) {
            hotelLiveOrderService.userDeleteCheckoutOrder(orderid);
        } else if (orderServiceType == 9) {
            hotelLiveOrderService.userDeleteContinueLiveOrder(orderid);
        } else {
            new RuntimeException("订单标识不符");
        }
    }

}
