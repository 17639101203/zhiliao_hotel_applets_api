package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailsReturn;
import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderDetailImpl implements ZlOrderDetailService {

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @Autowired
    private ZlOrderDetailMapper zlOrderDetailMapper;

    @Override
    public OrderDetailsReturn findOrder(OrderDetailInfoVO vo) {
        List<ZlOrderDetail> orderDetailsList = zlOrderDetailMapper.findOrderDetails(vo);
        if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
            ZlOrderDetail orderDetail = orderDetailsList.get(0);
            OrderDetailsReturn orderDetailsReturn = zlOrderMapper.find(orderDetail.getUserid(), orderDetail.getOrderserialno(), orderDetail.getBelongmodule());
            String roomNumber = zlOrderMapper.getRoomNumber(vo.getOrderid());
            orderDetailsReturn.setRoomNumber(roomNumber);
            if (orderDetailsReturn == null) {
                orderDetailsReturn = new OrderDetailsReturn();
            }
            orderDetailsReturn.setZlOrderDetailList(orderDetailsList);
            return orderDetailsReturn;
        }
        return null;
    }

}
