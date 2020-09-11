package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myOrder.dto.OrderInfoDTO;
import com.zhiliao.hotel.controller.myOrder.vo.*;
import com.zhiliao.hotel.model.ZlOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface ZlOrderMapper extends Mapper<ZlOrder> {

    OrderDetailsReturn find(@Param("userid") Long userid, @Param("orderserialno") String orderserialno, @Param("belongmodule") Short belongmodule);

    List<OrderVO> findAllOrder(@Param("orderInfoDTO") OrderInfoDTO orderInfoDTO);

    //取消订单
    //    void byOrderId(ZlOrder order);

    ZlOrder findById(@Param("orderid") Long orderid);

    //    void insertOrder(ZlOrder zlOrder);

    void updateOrder(@Param("out_trade_no") String out_trade_no, @Param("payDate") int payDate);

    List<OrderDetailVO> getOrderDetail(@Param("out_trade_no") String out_trade_no);

    OrderStatusVO getByOrderSerialNo(@Param("out_trade_no") String out_trade_no);

    void updateOrderUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("updateDate") Integer updateDate);

    void autoUpdateOrderUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("updateDate") Integer updateDate);

    List<OrderPayShortInfoVO> getOrderByOrderSerialNo(@Param("out_trade_no") String out_trade_no);

    void userDeleteOrder(@Param("orderSerialNo") String orderSerialNo, @Param("belongModule") Integer belongModule);

    Long waitForPayTotal(@Param("userid") Long userid);

    Long waitForGoodsTotal(@Param("userid") Long userid);

    Long allOrderTotal(@Param("userid") Long userid);

    String getRoomNumber(@Param("orderid") Long orderid);

    Integer getsupplierID(@Param("hotelGoodsSkuId") Integer hotelGoodsSkuId);

    void updateParentOrderID(@Param("parentid") Long parentid, @Param("parentOrderSerialNo") String parentOrderSerialNo);

    void updateAllOrderUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("updateDate") Integer updateDate);

    OrderStatusVO getByParentOrderSerialNo(@Param("out_trade_no") String out_trade_no);

    void updateAllOrder(@Param("out_trade_no") String out_trade_no, @Param("payDate") int payDate);

    void updateRefundStatus(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    ZlOrder getOrderByOrderId(@Param("orderId") Long orderId);

    void updateRefundStatus2(@Param("orderId") Long orderId, @Param("currertTime") Integer currertTime);

    void updateOrderRefundInfo(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    void updateRefundStatus3(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    BigDecimal getOrderActuallyPay(@Param("out_trade_no") String out_trade_no);

    BigDecimal getOrderActuallyPay2(@Param("out_trade_no") String out_trade_no);

    void signForOrder(@Param("orderId") Long orderId, @Param("currertTime") Integer currertTime);

    void updateRefundStatus4(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    void updateRefundStatus4Two(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);

    void updateRefundStatus5(@Param("orderid") Long orderid, @Param("currertTime") Integer currertTime);
}
