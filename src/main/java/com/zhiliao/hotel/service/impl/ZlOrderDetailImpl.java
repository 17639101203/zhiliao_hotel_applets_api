package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myOrder.dto.OrderDetailInfoDTO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailsReturn;
import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.mapper.ZlRefundRecordMapper;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.model.ZlRefundRecord;
import com.zhiliao.hotel.service.ZlOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private ZlRefundRecordMapper zlRefundRecordMapper;

    @Override
    public OrderDetailsReturn findOrder(Long orderId) {
        List<ZlOrderDetail> orderDetailsList = zlOrderDetailMapper.findOrderDetails(orderId);
        if (CollectionUtils.isEmpty(orderDetailsList)) {
            throw new BizException("参数有误!");
        }
        ZlOrderDetail orderDetail = orderDetailsList.get(0);
        OrderDetailsReturn orderDetailsReturn = zlOrderMapper.find(orderDetail.getUserid(), orderDetail.getOrderserialno(), orderDetail.getBelongmodule());
        if (orderDetailsReturn == null) {
            throw new BizException("参数有误!");
        }

        if (orderDetailsReturn.getRefundusertype() == 1) {
            ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
            zlRefundRecord.setOrderid(orderId);
            zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
            orderDetailsReturn.setUserremark(zlRefundRecord.getUserremark());
            orderDetailsReturn.setRefundmoney(zlRefundRecord.getRefundmoney());
        }

//        String roomNumber = zlOrderMapper.getRoomNumber(Long.valueOf(orderId));

        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(orderId);
        zlOrder = zlOrderMapper.selectOne(zlOrder);
        if (zlOrder == null) {
            throw new BizException("参数有误!");
        }
        orderDetailsReturn.setRoomNumber(zlOrder.getRoomnumber());
        orderDetailsReturn.setZlOrderDetailList(orderDetailsList);
        orderDetailsReturn.setIsUseCoupon(false);

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(zlOrder.getOrderid());
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);

        if (zlOrder.getCouponcash().intValue() > 0) {
            orderDetailsReturn.setIsUseCoupon(true);
            orderDetailsReturn.setCouponcash(zlOrder.getCouponcash());
        }

        if (zlOrder.getPaystatus() == 1 && zlOrder.getOrderstatus() == 0) {
            orderDetailsReturn.setStatusShowCount(1);
            orderDetailsReturn.setStatusShowInfo("待支付");
        } else if (zlOrder.getOrderstatus() == -1) {
            orderDetailsReturn.setStatusShowCount(2);
            orderDetailsReturn.setStatusShowInfo("已取消");
        } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 1 && zlOrder.getRefundstatus() == 0) {
            orderDetailsReturn.setStatusShowCount(3);
            orderDetailsReturn.setStatusShowInfo("已支付");
        } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 2 && zlOrder.getRefundstatus() == 0) {
            orderDetailsReturn.setStatusShowCount(4);
            orderDetailsReturn.setStatusShowInfo("已发货");
        } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 5 && zlOrder.getRefundstatus() == 0) {
            orderDetailsReturn.setStatusShowCount(5);
            orderDetailsReturn.setStatusShowInfo("已接单");
        } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 3 && zlOrder.getRefundstatus() == 0) {
            orderDetailsReturn.setStatusShowCount(11);
            orderDetailsReturn.setStatusShowInfo("已签收");
        } else if (zlOrder.getPaystatus() == 2 && (zlOrder.getOrderstatus() == 1 || zlOrder.getOrderstatus() == 2 || zlOrder.getOrderstatus() == 3)) {
            if (zlOrder.getRefundstatus() == 3) {
                orderDetailsReturn.setStatusShowCount(6);
                orderDetailsReturn.setStatusShowInfo("审核中");
            }
            if (zlOrder.getRefundstatus() == 1) {
                orderDetailsReturn.setStatusShowCount(6);
                orderDetailsReturn.setStatusShowInfo("审核中");
                if (zlRefundRecord.getIsusersend()) {
                    orderDetailsReturn.setIsmail(true);
                }
            }
            if (zlOrder.getRefundstatus() == 2) {
                if (zlRefundRecord != null && zlRefundRecord.getRefundtype() == 1 && (!zlRefundRecord.getIsusersend())) {
                    orderDetailsReturn.setStatusShowCount(13);
                    orderDetailsReturn.setStatusShowInfo("申请通过");
                }
            }
            if (zlOrder.getRefundstatus() == -12) {
                orderDetailsReturn.setStatusShowCount(7);
                orderDetailsReturn.setStatusShowInfo("退款关闭");
            }
            if (zlOrder.getRefundstatus() == -2) {
                orderDetailsReturn.setStatusShowCount(8);
                orderDetailsReturn.setStatusShowInfo("退款被驳回");
            }
            if (zlOrder.getRefundstatus() == -1) {
                orderDetailsReturn.setStatusShowCount(9);
                orderDetailsReturn.setStatusShowInfo("取消退款");
            }
            if (zlOrder.getRefundstatus() == 4) {
                orderDetailsReturn.setStatusShowCount(10);
                orderDetailsReturn.setStatusShowInfo("已退款");
            }
        } else if (zlOrder.getOrderstatus() == 4 || zlOrder.getOrderstatus() == 100) {
            orderDetailsReturn.setStatusShowCount(12);
            orderDetailsReturn.setStatusShowInfo("已完成");
        }

        return orderDetailsReturn;
    }

}
