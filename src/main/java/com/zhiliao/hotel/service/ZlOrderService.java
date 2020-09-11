package com.zhiliao.hotel.service;

import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.myOrder.dto.OrderInfoDTO;
import com.zhiliao.hotel.controller.myOrder.dto.OrderRefundAppealDTO;
import com.zhiliao.hotel.controller.myOrder.dto.RefundRecordDTO;
import com.zhiliao.hotel.controller.myOrder.dto.UploadExpressInfoDTO;
import com.zhiliao.hotel.controller.myOrder.params.GoodsInfoParam;
import com.zhiliao.hotel.controller.myOrder.vo.*;
import com.zhiliao.hotel.model.ZlExpress;
import com.zhiliao.hotel.model.ZlRefundCheckRecord;
import com.zhiliao.hotel.model.ZlSupplierAddress;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ZlOrderService {

    PageInfoResult findAllOrder(OrderInfoDTO orderInfoDTO);

    UserGoodsReturn submitOrder(HttpServletRequest request, Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoParam>> goodsInfoParamMap);

    void updateOrder(String out_trade_no);

    List<OrderDetailVO> getOrderDetail(String out_trade_no);

    void cancelOrder(String out_trade_no);

    void autoCancelOrder(String out_trade_no);

    OrderStatusVO getByOrderSerialNo(String out_trade_no);

    List<OrderPayShortInfoVO> getOrderByOrderSerialNo(String out_trade_no);

    void userDeleteOrder(String orderSerialNo, Integer belongModule);

    Long waitForPayTotal(Long userId);

    Long waitForGoodsTotal(Long userId);

    Long allOrderTotal(Long userId);

    void OrderNoticeToPhp(String out_trade_no);

    void orderRefundRecord(HttpServletRequest request, Long userID, RefundRecordDTO refundRecordDTO);

    void cancelRefundapply(Long orderId);

    void uploadExpressInfo(HttpServletRequest request, UploadExpressInfoDTO uploadExpressInfoDTO);

    List<ZlExpress> getExpressList();

    BigDecimal getOrderActuallyPay(String out_trade_no);

    BigDecimal getOrderActuallyPay2(String out_trade_no);

    void orderRefundAppeal(HttpServletRequest request, Long userID, OrderRefundAppealDTO orderRefundAppealDTO);

    List<OrderRefundHistoryVO> orderRefundHistory(Long orderId);

    ZlSupplierAddress getSupplierAddress(Long orderId);
}
