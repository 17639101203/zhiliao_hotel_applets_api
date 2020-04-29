package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.order.vo.GoodsInfoVO;
import com.zhiliao.hotel.controller.order.vo.HotelBasicVO;
import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.OrderSerialNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderServiceIml implements ZlOrderService {

    private final ZlOrderMapper orderMapper;

    private final ZlOrderDetailMapper orderDetailMapper;

    @Autowired
    public ZlOrderServiceIml(ZlOrderMapper orderMapper, ZlOrderDetailMapper orderDetailMapper) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public PageInfoResult findAllOrder(Long userID, Integer orderType, Integer orderStatus, Integer payStatus, Integer payType, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlOrder> dataList = orderMapper.findAllOrder(userID, orderType, orderStatus, payStatus, payType);
        PageInfo<ZlOrder> pageInfo = new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public void byOrderId(Long orderID) {
        ZlOrder order = orderMapper.findById(orderID);
        if (order != null) {
            order.setOrderStatus((byte) -1);
            order.setUpdateDate((int) new Date().getTime());
        }

        //修改订单表
        orderMapper.byOrderId(order);
        //修改订单详情表
        orderDetailMapper.byOrderdetailId(order);
    }

    @Override
    public List<ZlOrder> submitOrder(Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoVO>> goodsInfoMap) {
        //封装对象信息
        ZlOrder zlOrder = new ZlOrder();
        ZlOrderDetail zlOrderDetail = new ZlOrderDetail();
        List<ZlOrderDetail> zlOrderDetailList = new LinkedList<>();
        List<ZlOrder> zlOrderList = new LinkedList<>();

        zlOrder.setUserID(userID);
        zlOrder.setHotelID(hotelBasicVO.getHotelID());
        zlOrder.setHotelName(hotelBasicVO.getHotelName());
        zlOrder.setRoomID(hotelBasicVO.getRoomID());
        zlOrder.setRoomNumber(hotelBasicVO.getRoomNumber());

        Set<String> keySet = goodsInfoMap.keySet();

        BigDecimal totalPrice = new BigDecimal(0);
        //调用工具类生成订单编号
        String orderSerialNo = OrderSerialNoUtil.CreateOrderSerialNo("");
        for (String key : keySet) {
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoMap.get(key);
            String coverImgUrl = goodsInfoVOList.get(0).getCoverImgUrl();
            Integer orderType = goodsInfoVOList.get(0).getOrderType();

            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                BigDecimal price = goodsInfoVOList.get(i).getPrice();
                totalPrice = totalPrice.add(price);
            }
            zlOrder.setTotalPrice(totalPrice);
            zlOrder.setGoodsCoverUrl(coverImgUrl);
            zlOrder.setOrderType(orderType);
            zlOrder.setPayStatus(1);
            zlOrder.setIsDelete(0);
            zlOrder.setCreateDate(Math.toIntExact(System.currentTimeMillis() / 1000));

            zlOrder.setOrderSerialNo(orderSerialNo);

            orderMapper.insertOrder(zlOrder);
            zlOrderList.add(zlOrder);

            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                zlOrderDetail.setOrderID(zlOrder.getOrderID());
                zlOrderDetail.setUserID(userID);
                zlOrderDetail.setGoodsID(goodsInfoVOList.get(i).getGoodsID());
                zlOrderDetail.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                zlOrderDetail.setGoodsCoverUrl(goodsInfoVOList.get(i).getCoverImgUrl());
                zlOrderDetail.setPrice(goodsInfoVOList.get(i).getPrice());
                zlOrderDetail.setGoodsCount(goodsInfoVOList.get(i).getGoodsCount());
                zlOrderDetail.setIsDelete(0);
                zlOrderDetail.setCreateDate(Math.toIntExact(System.currentTimeMillis() / 1000));
                orderDetailMapper.insertOrderDetail(zlOrderDetail);
            }
        }
        return zlOrderList;
    }

    @Override
    public void updateOrder(String out_trade_no) {
        orderMapper.updateOrder(out_trade_no);
    }

    @Override
    public List<ZlOrderDetail> getOrderDetail(String out_trade_no) {
        List<ZlOrderDetail> zlOrderDetailList = orderMapper.getOrderDetail(out_trade_no);
        return zlOrderDetailList;
    }
}
