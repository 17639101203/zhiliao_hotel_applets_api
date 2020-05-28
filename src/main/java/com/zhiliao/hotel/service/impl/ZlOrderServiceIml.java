package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.*;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private ZlOrderMapper orderMapper;
    @Autowired
    private ZlOrderDetailMapper zlOrderDetailMapper;

    @Autowired
    private ZlCouponMapper zlCouponMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlGoodsMapper zlGoodsMapper;

    @Autowired
    private ZlHotelDetailMapper zlHotelDetailMapper;

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @Autowired
    private  ZlHotelgoodsskuMapper zlHotelgoodsskuMapper;

    @Override
    public PageInfoResult findAllOrder(OrderInfoVO vo) {

        List<OrderList> allOrders = orderMapper.findAllOrder(vo);
        if (allOrders != null && !allOrders.isEmpty()) {
            List<ZlOrderDetail> goods = null;
            Long goodsTotal = null;
            for (OrderList order : allOrders) {
                goods = zlOrderDetailMapper.findGoods(order.getUserid(), order.getBelongmodule());
                goodsTotal = zlOrderDetailMapper.countGoods(order.getUserid(), order.getBelongmodule());
                order.setZlOrderDetailList(goods);
                order.setGoodsTotal(goodsTotal);
            }
        }
        PageInfo<OrderList> pageInfo = new PageInfo<>(allOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public UserGoodsReturn submitOrder(Long userID,
                                       HotelBasicVO hotelBasicVO,
                                       Map<String, List<GoodsInfoVO>> goodsInfoMap) {

        if (goodsInfoMap.size() == 1) {
            //集合数量为1,说明只有一个模块商品数据
            Set<String> keySet = goodsInfoMap.keySet();
            for (String key : keySet) {
                List<GoodsInfoVO> goodsInfoVOList = goodsInfoMap.get(key);
                //获取订单号
                String orderSerialNo = goodsInfoVOList.get(0).getOrderSerialNo();
                //如果订单号不为空,说明是被拆单的订单
                if (StringUtils.isNoneBlank(orderSerialNo)) {
                    //先删除总订单中该小模块的数据信息
                    Short belongModuleVO = goodsInfoVOList.get(0).getBelongModule();
                    int belongModule = belongModuleVO;
                    this.cancelOrder(orderSerialNo, belongModule);
                }
            }
        }

        //封装商品短信息对象
        List<GoodsShortInfoVO> goodsShortInfoVOList = new LinkedList<>();

        //封装商品优惠券对象
        List<GoodsCouponInfoVO> goodsCouponInfoVOList = new LinkedList<>();

        //封装用户商品信息并返回
        UserGoodsReturn userGoodsReturn = new UserGoodsReturn();

        //查询该酒店的配送费
        BigDecimal sendCash = zlHotelDetailMapper.findSendCashByHotelID(hotelBasicVO.getHotelID());

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

        //调用工具类生成订单编号
        String orderSerialNo = OrderIDUtil.createOrderID("");
        for (String key : keySet) {
            //计算各个模块总金额
            BigDecimal totalPrice = new BigDecimal(0);
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
            Short belongModule = goodsInfoVOList.get(0).getBelongModule();

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
                BigDecimal goodsCount = BigDecimal.valueOf(goodsInfoVOList.get(i).getGoodsCount());
                totalPrice = totalPrice.add(price.multiply(goodsCount));
            }
            totalPrice = totalPrice.add(sendCash);
            zlOrder.setTotalprice(totalPrice);

            //获取优惠券自增id
            Integer recID = goodsInfoVOList.get(0).getRecID();
            //获取备注信息
            String remark = goodsInfoVOList.get(0).getRemark();

            //如果用户优惠券自增id不为-1,说明用户在该模块使用了优惠券
            if (recID != -1) {
                //根据优惠券id查询优惠券信息
                CouponUserVO couponUserVO = zlCouponMapper.findByRecID(recID);
                //封装优惠券对象,放入list集合存入redis
                GoodsCouponInfoVO goodsCouponInfoVO = new GoodsCouponInfoVO();
                goodsCouponInfoVO.setRecID(recID);
                goodsCouponInfoVO.setUserID(couponUserVO.getUserID());
                goodsCouponInfoVO.setCouponID(couponUserVO.getCouponID());
                goodsCouponInfoVO.setBelongModule(belongModule);

                goodsCouponInfoVOList.add(goodsCouponInfoVO);

                //将该优惠券放入redis,进行锁定,
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_RECID + recID, recID);

                //算出用户在这个模块的实际支付金额
                BigDecimal actuallyPay = totalPrice.subtract(couponUserVO.getDiscountMoney());
                zlOrder.setActuallypay(actuallyPay);
                zlOrder.setCouponid(couponUserVO.getCouponID());
            } else {
                //优惠券id为-1说明没有使用优惠券,实际支付金额=总价
                zlOrder.setActuallypay(totalPrice);
            }
            if (StringUtils.isNoneBlank(remark)) {
                //备注信息不为空且不是空串
                zlOrder.setRemark(remark);
            }

            zlOrder.setGoodscoverurl(coverImgUrl);
            zlOrder.setBelongmodule(belongModule);
            zlOrder.setPaystatus((byte) 1);
            zlOrder.setIsdelete(false);
            zlOrder.setOrderstatus((byte) 0);
            zlOrder.setPaytype((byte) 1);
            zlOrder.setComeformid(1);
            zlOrder.setRefundstatus((byte) 1);
            zlOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
//            zlOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            zlOrder.setRefundcount((byte) 0);

            //获取订单号
            String orderSerialNoOld = goodsInfoMap.get(key).get(0).getOrderSerialNo();
            //如果订单号不为空,说明是被拆单的订单
            if (StringUtils.isNoneBlank(orderSerialNoOld)) {
                zlOrder.setParentorderserialno(orderSerialNoOld);
            }
            zlOrder.setOrderserialno(orderSerialNo);

            //将订单数据存入数据库
            orderMapper.insert(zlOrder);
            zlOrderList.add(zlOrder);

            for (int i = 0; i < goodsInfoVOList.size(); i++) {
                //封装订单详情信息
                ZlOrderDetail zlOrderDetail = new ZlOrderDetail();
                //获取用户选择的商品hotelGoodsSkuID和数量
                Integer hotelGoodsSkuID = goodsInfoVOList.get(i).getHotelGoodsSkuID();
                Integer goodsCount = goodsInfoVOList.get(i).getGoodsCount();
                if (redisTemplate.hasKey(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID)) {
                    //如果存在该键,就更新商品数量
                    Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, goodsCount + count);
                } else {
                    //不存在该键,就新建
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, goodsCount);
                }
                zlOrderDetail.setOrderid(zlOrder.getOrderid());
                zlOrderDetail.setUserid(userID);
                zlOrderDetail.setHotelgoodsid(hotelGoodsSkuID);
                zlOrderDetail.setGoodsname(goodsInfoVOList.get(i).getGoodsName());
                zlOrderDetail.setGoodscoverurl(goodsInfoVOList.get(i).getCoverImgUrl());
                zlOrderDetail.setPrice(goodsInfoVOList.get(i).getPrice());
                zlOrderDetail.setGoodscount(goodsInfoVOList.get(i).getGoodsCount());
                zlOrderDetail.setBelongmodule(goodsInfoVOList.get(i).getBelongModule());
                zlOrderDetail.setIsdelete(false);
                zlOrderDetail.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
//                zlOrderDetail.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

                //封装订单商品短信息,并存入列表,准备放入redis
                GoodsShortInfoVO goodsShortInfoVO = new GoodsShortInfoVO();
                goodsShortInfoVO.setGoodsCount(goodsInfoVOList.get(i).getGoodsCount());
                goodsShortInfoVO.setHotelGoodsSkuID(hotelGoodsSkuID);
                goodsShortInfoVO.setBelongModule(goodsInfoVOList.get(i).getBelongModule());
                goodsShortInfoVO.setHotelID(hotelBasicVO.getHotelID());
                goodsShortInfoVOList.add(goodsShortInfoVO);
                //将订单详情数据存入数据库
                zlOrderDetailMapper.insert(zlOrderDetail);
            }
        }
        //将该订单商品放入redis,进行锁定
        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + orderSerialNo, goodsShortInfoVOList);
        //将该订单商品标记放入redis,时间最长为5分钟
        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + orderSerialNo, goodsShortInfoVOList, 1, TimeUnit.MINUTES);

        if (goodsCouponInfoVOList.size() > 0) {
            //将该订单优惠券集合放入redis
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + orderSerialNo, goodsCouponInfoVOList);
        }

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
                //获取用户选择的商品hotelGoodsSkuID和数量
                Integer hotelGoodsSkuID = goodsInfoVOList.get(i).getHotelGoodsSkuID();
                Integer goodsCount = goodsInfoVOList.get(i).getGoodsCount();
                //判断酒店该商品库存是否足够
                Integer stockCount = zlHotelgoodsskuMapper.getStockCount(hotelID, hotelGoodsSkuID);
                if (stockCount > goodsCount) {
                    Boolean bool = redisTemplate.hasKey(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                    Integer count = 0;
                    if (bool) {
                        //存在该键,就查询出该商品被锁定的库存数量
                        count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                    }
                    if (stockCount - count > goodsCount) {
                        continue;
                    } else {
                        goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                        goodsStockCountNo.setHotelGoodsSkuID(goodsInfoVOList.get(i).getHotelGoodsSkuID());
                        goodsStockCountNoList.add(goodsStockCountNo);
                    }
                } else {
                    goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                    goodsStockCountNo.setHotelGoodsSkuID(goodsInfoVOList.get(i).getHotelGoodsSkuID());
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
    public List<OrderDetailVO> getOrderDetail(String out_trade_no) {
        List<OrderDetailVO> zlOrderDetailList = orderMapper.getOrderDetail(out_trade_no);
        return zlOrderDetailList;
    }

    @Override
    public void cancelOrder(String out_trade_no, Integer belongModule) {
        //拿出存在redis的订单商品短信息集合
        List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
        for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
            Integer belongModuleVO = Integer.valueOf(goodsShortInfoVO.getBelongModule());
            if (belongModuleVO.equals(belongModule)) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();
                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
            }
        }

        //利用迭代器遍历删除该模块下的商品
        Iterator<GoodsShortInfoVO> iteratorGoodsShortInfo = goodsShortInfoVOList.iterator();
        while (iteratorGoodsShortInfo.hasNext()) {
            GoodsShortInfoVO goodsShortInfoVO = iteratorGoodsShortInfo.next();
            Integer belongModuleVO = Integer.valueOf(goodsShortInfoVO.getBelongModule());
            if (belongModuleVO.equals(belongModule)) {
                iteratorGoodsShortInfo.remove();
            }
        }

        if (goodsShortInfoVOList.size() == 0) {
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + out_trade_no);
        } else {
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no, goodsShortInfoVOList);
        }

        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        //修改数据库支付/取消时间
        zlOrderMapper.updateOrderUpdateDate(out_trade_no, belongModule, updateDate);
        zlOrderDetailMapper.updateOrderDetailUpdateDate(out_trade_no, belongModule, updateDate);

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Integer belongModuleVO = Integer.valueOf(goodsCouponInfoVO.getBelongModule());
                if (belongModuleVO.equals(belongModule)) {
                    Integer recID = goodsCouponInfoVO.getRecID();
                    //删除redis中锁定的优惠券
                    redisTemplate.delete(RedisKeyConstant.ORDER_RECID + recID);
                    iteratorGoodsCouponInfo.remove();
                    break;
                }
            }

            if (goodsCouponInfoVOList.size() == 0) {
                redisTemplate.delete(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);
            } else {
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no, goodsCouponInfoVOList);
            }
        }
    }

    @Override
    public void autoCancelOrder(String out_trade_no) {
        //拿出存在redis的订单商品短信息集合
        List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
        for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
            Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
            Integer goodsCount = goodsShortInfoVO.getGoodsCount();
            //更新redis该商品数量(删除redis中锁定的商品数量)
            Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
        }
        //删除redis中锁定的订单商品
        redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);

        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        //修改数据库支付/取消时间
        zlOrderMapper.autoUpdateOrderUpdateDate(out_trade_no, updateDate);
        zlOrderDetailMapper.autoUpdateOrderDetailUpdateDate(out_trade_no, updateDate);

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);
            for (GoodsCouponInfoVO goodsCouponInfoVO : goodsCouponInfoVOList) {
                Integer recID = goodsCouponInfoVO.getRecID();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_RECID + recID);
            }
            //删除redis中锁定的优惠券集合
            redisTemplate.delete(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);
        }
    }

    @Override
    public OrderStatusVO getByOrderSerialNo(String out_trade_no) {
        OrderStatusVO orderStatusVO = zlOrderMapper.getByOrderSerialNo(out_trade_no);
        return null;
    }

    //判断用户选择的商品是否库存充足,并返回库存不足商品的集合
    private List<GoodsStockCountNo> judgeSmallStock(HotelBasicVO hotelBasicVO, List<GoodsInfoVO> goodsInfoVOList) {
        //封装库存不足商品信息
        GoodsStockCountNo goodsStockCountNo = new GoodsStockCountNo();
        List<GoodsStockCountNo> goodsStockCountNoList = new LinkedList<>();
        Integer hotelID = hotelBasicVO.getHotelID();
        String orderSerialNo = goodsInfoVOList.get(0).getOrderSerialNo();
        if (StringUtils.isNoneBlank(orderSerialNo)) {

        }

        for (int i = 0; i < goodsInfoVOList.size(); i++) {

            //获取用户选择的商品hotelGoodsSkuID和数量
            Integer hotelGoodsSkuID = goodsInfoVOList.get(i).getHotelGoodsSkuID();
            Integer goodsCount = goodsInfoVOList.get(i).getGoodsCount();
            //判断酒店该商品库存是否足够
            Integer stockCount = zlHotelgoodsskuMapper.getStockCount(hotelID, hotelGoodsSkuID);
            if (stockCount > goodsCount) {
                Boolean bool = redisTemplate.hasKey(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                Integer count = 0;
                if (bool) {
                    //存在该键,就查询出该商品被锁定的库存数量
                    count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                }
                if (stockCount - count > goodsCount) {
                    continue;
                } else {
                    goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                    goodsStockCountNo.setHotelGoodsSkuID(goodsInfoVOList.get(i).getHotelGoodsSkuID());
                    goodsStockCountNoList.add(goodsStockCountNo);
                }
            } else {
                goodsStockCountNo.setGoodsName(goodsInfoVOList.get(i).getGoodsName());
                goodsStockCountNo.setHotelGoodsSkuID(goodsInfoVOList.get(i).getHotelGoodsSkuID());
                goodsStockCountNoList.add(goodsStockCountNo);
            }
        }

        return goodsStockCountNoList;
    }

}
