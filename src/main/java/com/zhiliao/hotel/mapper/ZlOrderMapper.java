package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailVO;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderMapper extends Mapper<ZlOrder> {

    List<ZlOrder> findAllOrder(@Param("userID") Long userID, @Param("orderType") Integer orderType, @Param("orderStatus") Integer orderStatus, @Param("payStatus") Integer payStatus, @Param("payType") Integer payType);

    //取消订单
    void byOrderId(ZlOrder order);

    ZlOrder findById(@Param("orderid") Long orderid);

//    void insertOrder(ZlOrder zlOrder);

    void updateOrder(String out_trade_no);

    List<OrderDetailVO> getOrderDetail(String out_trade_no);
}
