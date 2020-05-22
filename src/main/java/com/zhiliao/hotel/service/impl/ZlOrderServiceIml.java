package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsStockCountNo;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.controller.myOrder.vo.UserGoodsReturn;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlCoupon;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderServiceIml implements ZlOrderService {

    private final ZlOrderMapper orderMapper;

    private final ZlOrderDetailMapper orderDetailMapper;

    private ZlCouponMapper zlCouponMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private ZlGoodsMapper zlGoodsMapper;

    @Autowired
    private ZlHotelGoodsMapper zlHotelGoodsMapper;

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
            order.setOrderstatus((byte) -1);
            order.setUpdatedate((int) new Date().getTime());
        }

        //修改订单表
        orderMapper.byOrderId(order);
        //修改订单详情表
        orderDetailMapper.byOrderdetailId(order);
    }

    @Override
    public UserGoodsReturn submitOrder(Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoVO>> goodsInfoMap) {

        //封装用户商品信息并返回
        UserGoodsReturn userGoodsReturn = new UserGoodsReturn();

        //调用判断用户选择的商品是否库存充足
        List<GoodsStockCountNo> goodsStockCountNoList = judgeStock(hotelBasicVO, goodsInfoMap);
        if (goodsStockCountNoList.size() > 0) {
            //集合长度大于0,说明有库存不足的商品
            userGoodsReturn.setUserGoodsInfoList(goodsStockCountNoList);
            userGoodsReturn.setContent("返回的列表商品库存不足,请处理！");
            return userGoodsReturn;
        }

        //封装订单信息列表
        List<ZlOrder> zlOrderList = new LinkedList<>();

        Set<String> keySet = goodsInfoMap.keySet();

        BigDecimal totalPrice = new BigDecimal(0);
        //调用工具类生成订单编号
        String orderSerialNo = OrderIDUtil.createOrderID("");
        for (String key : keySet) {
            //封装订单信息
            ZlOrder zlOrder = new ZlOrder();
            zlOrder.setUserid(userID);
            zlOrder.setHotelid(hotelBasicVO.getHotelID());
            zlOrder.setHotelname(hotelBasicVO.getHotelName());
            zlOrder.setRoomid(hotelBasicVO.getRoomID());
            zlOrder.setRoomnumber(hotelBasicVO.getRoomNumber());
            //获取每个模块类型的商品数据
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoMap.get(key);
            //获取该模块类型商品的第一张商品图片地址
            String coverImgUrl = goodsInfoVOList.get(0).getCoverImgUrl();
            //获取该模块类型商品的类型
            Byte orderType = goodsInfoVOList.get(0).getOrderType();
            //获取该模块类型商品的优惠券id
            Integer couponID = goodsInfoVOList.get(0).getCouponID();
            //如果该模块是土特产,则需要判断是否需要配送
            if (key.equals("TTC")) {
                String deliveryAddress = goodsInfoVOList.get(0).getDeliveryAddress();
                //不为空说明需要配送
                if (deliveryAddress != null) {
                    zlOrder.setDeliveryaddress(deliveryAddress);
                }
            }

            //遍历每个模块类型的商品价格并相加
            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                BigDecimal price = goodsInfoVOList.get(i).getPrice();
                totalPrice = totalPrice.add(price);
            }
            zlOrder.setTotalprice(totalPrice);
            //如果优惠券id不为-1,说明用户在该模块使用了优惠券
            if (couponID != -1) {
                //根据优惠券id查询优惠券信息
                ZlCoupon zlCoupon = zlCouponMapper.findByCouponID(couponID);

                //将该优惠券放入redis,进行锁定,时间最长为5分钟
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONID + couponID, couponID, 5, TimeUnit.MINUTES);
                //算出用户在这个模块的实际支付金额
                BigDecimal actuallyPay = totalPrice.subtract(zlCoupon.getDiscountmoney());
                zlOrder.setActuallypay(actuallyPay);
                zlOrder.setCouponid(couponID);
            } else {
                //优惠券id为-1说明没有使用优惠券,实际支付金额=总价
                zlOrder.setActuallypay(totalPrice);
            }

            zlOrder.setGoodscoverurl(coverImgUrl);
            zlOrder.setOrdertype(orderType);
            zlOrder.setPaystatus((byte) 1);
            zlOrder.setIsdelete(false);
            zlOrder.setOrderstatus((byte) 0);
            zlOrder.setPaytype((byte) 1);
            zlOrder.setComeformid(1);
            zlOrder.setRefundstatus((byte) 1);
            zlOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            zlOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            zlOrder.setRefundcount((byte) 0);

            zlOrder.setOrderserialno(orderSerialNo);

            //将订单数据存入数据库
//            orderMapper.insertOrder(zlOrder);
            orderMapper.insert(zlOrder);
            zlOrderList.add(zlOrder);

            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                //封装订单详情信息
                ZlOrderDetail zlOrderDetail = new ZlOrderDetail();
                //获取用户选择的商品skuid和数量
                Integer skuID = goodsInfoVOList.get(i).getSkuID();
                Integer goodsCount = goodsInfoVOList.get(i).getGoodsCount();
                if (redisTemplate.hasKey(RedisKeyConstant.ORDER_SKU_ID + skuID)) {
                    //如果存在该键,就更新商品数量
                    Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_SKU_ID + skuID);
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_SKU_ID + skuID, goodsCount + count);
                } else {
                    //不存在该键,就新建
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_SKU_ID + skuID, goodsCount);
                }
                zlOrderDetail.setOrderid(zlOrder.getOrderid());
                zlOrderDetail.setUserid(userID);
                zlOrderDetail.setHotelgoodsid(goodsInfoVOList.get(i).getGoodsID());
                zlOrderDetail.setGoodsname(goodsInfoVOList.get(i).getGoodsName());
                zlOrderDetail.setGoodscoverurl(goodsInfoVOList.get(i).getCoverImgUrl());
                zlOrderDetail.setPrice(goodsInfoVOList.get(i).getPrice());
                zlOrderDetail.setGoodscount(goodsInfoVOList.get(i).getGoodsCount());
                zlOrderDetail.setIsdelete(false);
                zlOrderDetail.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
                zlOrderDetail.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
                //将订单详情数据存入数据库
//                orderDetailMapper.insertOrderDetail(zlOrderDetail);
               orderDetailMapper.insert(zlOrderDetail);
            }
        }
        //将该订单放入redis,时间最长为5分钟
        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + orderSerialNo, orderSerialNo, 5, TimeUnit.MINUTES);
        userGoodsReturn.setUserGoodsInfoList(zlOrderList);
        userGoodsReturn.setContent("提交订单成功,请进行下单操作!");
        return userGoodsReturn;
    }

    //判断用户选择的商品是否库存充足,并返回库存不足商品的集合
    private List<GoodsStockCountNo> judgeStock(HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoVO>> goodsInfoMap) {
        //封装库存不足商品信息
        GoodsStockCountNo goodsStockCountNo = new GoodsStockCountNo();
        List<GoodsStockCountNo> goodsStockCountNoList = new LinkedList<>();
        Set<String> keySet = goodsInfoMap.keySet();
        Integer hotelID = hotelBasicVO.getHotelID();

        for (String key : keySet) {
            //获取每个模块类型的商品数据
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoMap.get(key);

            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                //获取用户选择的商品skuid和数量
                Integer skuID = goodsInfoVOList.get(i).getSkuID();
                Integer goodsCount = goodsInfoVOList.get(i).getGoodsCount();
                //判断酒店该商品库存是否足够
                Integer stockCount = zlHotelGoodsMapper.getStockCount(hotelID, skuID);
//                System.err.println(stockCount);
                if (stockCount > goodsCount) {
                    Boolean bool = redisTemplate.hasKey(RedisKeyConstant.ORDER_SKU_ID + skuID);
                    Integer count = 0;
                    if (bool) {
                        //存在该键,就查询出该商品被锁定的库存数量
                        count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_SKU_ID + skuID);
                    }
                    if (stockCount - count > goodsCount) {
                        continue;
                    } else {
                        goodsStockCountNo.setGoodsID(goodsInfoVOList.get(i).getGoodsID());
                        goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                        goodsStockCountNo.setSkuID(goodsInfoVOList.get(i).getSkuID());
                        goodsStockCountNoList.add(goodsStockCountNo);
                    }
                } else {
                    goodsStockCountNo.setGoodsID(goodsInfoVOList.get(i).getGoodsID());
                    goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                    goodsStockCountNo.setSkuID(goodsInfoVOList.get(i).getSkuID());
                    goodsStockCountNoList.add(goodsStockCountNo);
                }
            }
        }

        return goodsStockCountNoList;
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
