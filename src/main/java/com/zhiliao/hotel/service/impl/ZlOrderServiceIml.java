package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.controller.myOrder.dto.OrderInfoDTO;
import com.zhiliao.hotel.controller.myOrder.dto.OrderRefundAppealDTO;
import com.zhiliao.hotel.controller.myOrder.dto.RefundRecordDTO;
import com.zhiliao.hotel.controller.myOrder.dto.UploadExpressInfoDTO;
import com.zhiliao.hotel.controller.myOrder.params.GoodsInfoParam;
import com.zhiliao.hotel.controller.myOrder.util.IpUtils;
import com.zhiliao.hotel.controller.myOrder.vo.*;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlOrderService;
import com.zhiliao.hotel.utils.IPUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlOrderServiceIml implements ZlOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ZlOrderServiceIml.class);


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
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ZlCouponuserMapper zlCouponuserMapper;

    @Autowired
    private ZlOrderParentMapper zlOrderParentMapper;

    @Autowired
    private ZlCartMapper zlCartMapper;

    @Autowired
    private ZlRefundRecordMapper zlRefundRecordMapper;

    @Autowired
    private ZlRefundCheckRecordMapper zlRefundCheckRecordMapper;

    @Autowired
    private ZlExpressMapper zlExpressMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private ZlSupplierAddresMapper zlSupplierAddresMapper;

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Override
    public PageInfoResult findAllOrder(OrderInfoDTO orderInfoDTO) {

        if (orderInfoDTO.getBelongmodule() != null) {
            Short belongmodule = orderInfoDTO.getBelongmodule();
            if (belongmodule != 1 && belongmodule != 2 && belongmodule != 3 && belongmodule != 4) {
                throw new BizException("参数有误!");
            }
        }

        //分页插件 pageNo:起始页，pageSize：每页显示条数
        PageHelper.startPage(orderInfoDTO.getPageNo(), orderInfoDTO.getPageSize());
        List<OrderVO> zlOrderList = zlOrderMapper.findAllOrder(orderInfoDTO);
        PageInfo<OrderVO> orderVOPageInfo = new PageInfo<OrderVO>(zlOrderList);

        List<OrderVO> orderVOAllList = new LinkedList<>();
        List<OrderVO> orderVOToBePayList = new LinkedList<>();
        List<OrderVO> orderVOToBeReceivedAllList = new LinkedList<>();
        for (OrderVO zlOrder : zlOrderList) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(zlOrder, orderVO);
            List<ZlOrderDetail> goodsList = zlOrderDetailMapper.find2Goods(zlOrder.getUserid(), zlOrder.getOrderserialno(), zlOrder.getBelongmodule());
            Long goodsTotal = zlOrderDetailMapper.countGoods(zlOrder.getUserid(), zlOrder.getOrderserialno(), zlOrder.getBelongmodule());
            orderVO.setZlOrderDetailList(goodsList);
            orderVO.setGoodsTotal(goodsTotal);

            ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
            zlRefundRecord.setOrderid(zlOrder.getOrderid());
            zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);

            if (zlOrder.getPaystatus() == 1 && zlOrder.getOrderstatus() == 0) {
                orderVO.setStatusShowCount(1);
                orderVO.setStatusShowInfo("待支付");
                orderVOAllList.add(orderVO);
                orderVOToBePayList.add(orderVO);
            } else if (zlOrder.getOrderstatus() == -1) {
                orderVO.setStatusShowCount(2);
                orderVO.setStatusShowInfo("已取消");
                orderVOAllList.add(orderVO);
            } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 1 && zlOrder.getRefundstatus() == 0) {
                orderVO.setStatusShowCount(3);
                orderVO.setStatusShowInfo("已支付");
                orderVOAllList.add(orderVO);
            } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 2 && zlOrder.getRefundstatus() == 0) {
                orderVO.setStatusShowCount(4);
                orderVO.setStatusShowInfo("已发货");
                orderVOAllList.add(orderVO);
                orderVOToBeReceivedAllList.add(orderVO);
            } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 3 && zlOrder.getRefundstatus() == 0) {
                orderVO.setStatusShowCount(11);
                orderVO.setStatusShowInfo("已签收");
                orderVOAllList.add(orderVO);
            } else if (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 5 && zlOrder.getRefundstatus() == 0) {
                orderVO.setStatusShowCount(5);
                orderVO.setStatusShowInfo("已接单");
                orderVOAllList.add(orderVO);
                orderVOToBeReceivedAllList.add(orderVO);
            } else if (zlOrder.getPaystatus() == 2 && (zlOrder.getOrderstatus() == 1 || zlOrder.getOrderstatus() == 2 || zlOrder.getOrderstatus() == 3)) {
                if (zlOrder.getRefundstatus() == 3) {
                    orderVO.setStatusShowCount(6);
                    orderVO.setStatusShowInfo("审核中");
                    orderVOAllList.add(orderVO);
                }
                if (zlOrder.getRefundstatus() == 1) {
                    orderVO.setStatusShowCount(6);
                    orderVO.setStatusShowInfo("审核中");
                    if (zlRefundRecord.getIsusersend()) {
                        orderVO.setIsmail(true);
                    }
                    orderVOAllList.add(orderVO);
                }
                if (zlOrder.getRefundstatus() == 2) {
                    if (zlRefundRecord != null && zlRefundRecord.getRefundtype() == 1 && (!zlRefundRecord.getIsusersend())) {
                        orderVO.setStatusShowCount(13);
                        orderVO.setStatusShowInfo("申请通过");
                        orderVOAllList.add(orderVO);
                    }
                }
                if (zlOrder.getRefundstatus() == -12) {
                    orderVO.setStatusShowCount(7);
                    orderVO.setStatusShowInfo("退款关闭");
                    orderVOAllList.add(orderVO);
                }
                if (zlOrder.getRefundstatus() == -2) {
                    orderVO.setStatusShowCount(8);
                    orderVO.setStatusShowInfo("退款被驳回");
                    orderVOAllList.add(orderVO);
                }
                if (zlOrder.getRefundstatus() == -1) {
                    orderVO.setStatusShowCount(9);
                    orderVO.setStatusShowInfo("取消退款");
                    orderVOAllList.add(orderVO);
                }
                if (zlOrder.getRefundstatus() == 4) {
                    orderVO.setStatusShowCount(10);
                    orderVO.setStatusShowInfo("已退款");
                    orderVOAllList.add(orderVO);
                }
            } else if (zlOrder.getOrderstatus() == 4 || zlOrder.getOrderstatus() == 100) {
                orderVO.setStatusShowCount(12);
                orderVO.setStatusShowInfo("已完成");
                orderVOAllList.add(orderVO);
            }
        }

        PageInfo<OrderVO> pageInfo = null;
        if (orderInfoDTO.getOrderstatus() == 0) {
            pageInfo = new PageInfo<>(orderVOAllList);
        } else if (orderInfoDTO.getOrderstatus() == 1) {
            pageInfo = new PageInfo<>(orderVOToBePayList);
        } else if (orderInfoDTO.getOrderstatus() == 2) {
            pageInfo = new PageInfo<>(orderVOToBeReceivedAllList);
        }
        pageInfo.setTotal(orderVOPageInfo.getTotal());
        pageInfo.setPageNum(orderVOPageInfo.getPageNum());
        pageInfo.setPageSize(orderVOPageInfo.getPageSize());
        int remainder = Math.toIntExact(orderVOPageInfo.getTotal() % orderVOPageInfo.getPageSize());
        int pages = Math.toIntExact(orderVOPageInfo.getTotal() / orderVOPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public UserGoodsReturn submitOrder(HttpServletRequest request, Long userID,
                                       HotelBasicVO hotelBasicVO,
                                       Map<String, List<GoodsInfoParam>> goodsInfoParamMap) {

        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(hotelBasicVO.getRoomNumber(), hotelBasicVO.getHotelID());
        if (zlHotelroom == null) {
            throw new BizException("该房间不存在,详情请咨询酒店前台!");
        }

        int orderFlag = 0;

        if (goodsInfoParamMap.size() == 1) {
            //订单为单模块
            orderFlag = 1;
            //集合数量为1,说明只有一个模块商品数据
            Set<String> keySet = goodsInfoParamMap.keySet();
            for (String key : keySet) {
                List<GoodsInfoParam> goodsInfoParamList = goodsInfoParamMap.get(key);
                String orderSerialNo = goodsInfoParamList.get(0).getOrderSerialNo();
                //如果订单号不为空,说明是被拆单的订单
                if (StringUtils.isNoneBlank(orderSerialNo)) {
                    //订单为多模块下的单模块
                    orderFlag = 2;
                    //先删除总订单中该小模块的数据信息
                    Short belongModuleVO = goodsInfoParamList.get(0).getBelongModule();
                    int belongModule = belongModuleVO;
//                    this.cancelOrder(orderSerialNo, belongModule);
                    this.deleteRedisAllOrder(orderSerialNo);
                }
            }
        }

        //提交订单
        UserGoodsReturn userGoodsReturn = this.submitManyOrder(request, userID, hotelBasicVO, goodsInfoParamMap, orderFlag);
        return userGoodsReturn;

    }

    //判断用户选择的商品是否库存充足,并返回库存不足商品的集合
    private List<GoodsStockCountNo> judgeStock(HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoParam>> goodsInfoParamMap) {

        List<GoodsStockCountNo> goodsStockCountNoList = new LinkedList<>();
        Set<String> keySet = goodsInfoParamMap.keySet();
        Integer hotelID = hotelBasicVO.getHotelID();

        for (String key : keySet) {
            //获取每个模块类型的商品数据
            List<GoodsInfoParam> goodsInfoParamList = goodsInfoParamMap.get(key);

            for (int i = 0; i < goodsInfoParamList.size(); i++) {
                //获取用户选择的商品hotelGoodsSkuID和数量
                Integer hotelGoodsSkuID = goodsInfoParamList.get(i).getHotelGoodsSkuId();
                Integer goodsCount = goodsInfoParamList.get(i).getGoodsCount();
                String goodsName = goodsInfoParamList.get(i).getGoodsName();
                //判断酒店该商品库存是否足够
                Integer stockCount = zlGoodsMapper.getStockCount(hotelID, hotelGoodsSkuID);
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
                        //封装库存不足商品信息
                        GoodsStockCountNo goodsStockCountNo = new GoodsStockCountNo();
                        goodsStockCountNo.setGoodsName(goodsName);
                        goodsStockCountNo.setHotelGoodsSkuID(hotelGoodsSkuID);
                        goodsStockCountNoList.add(goodsStockCountNo);
                    }
                } else {
                    //封装库存不足商品信息
                    GoodsStockCountNo goodsStockCountNo = new GoodsStockCountNo();
                    goodsStockCountNo.setGoodsName(goodsName);
                    goodsStockCountNo.setHotelGoodsSkuID(hotelGoodsSkuID);
//                    System.out.println(goodsStockCountNo.getGoodsName());
                    goodsStockCountNoList.add(goodsStockCountNo);
                }
            }
        }

        return goodsStockCountNoList;
    }

    @Override
    public void updateOrder(String out_trade_no) {
        int payDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        if (out_trade_no.startsWith("SM")) {
            zlOrderMapper.updateAllOrder(out_trade_no, payDate);
        } else {
            zlOrderMapper.updateOrder(out_trade_no, payDate);
        }
    }

    @Override
    public List<OrderDetailVO> getOrderDetail(String out_trade_no) {
        List<OrderDetailVO> zlOrderDetailList = zlOrderMapper.getOrderDetail(out_trade_no);
        return zlOrderDetailList;
    }

    @Override
    public void cancelOrder(String out_trade_no) {

        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderserialno(out_trade_no);
        zlOrder = zlOrderMapper.selectOne(zlOrder);

        if (zlOrder == null) {
            throw new BizException("订单编号有误,请重新填写");
        }
        String parentorderserialno = zlOrder.getParentorderserialno();

        //拿出存在redis的订单商品短信息集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno)) {
            List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno);
            for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();
                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                if (count.equals(goodsCount)) {
                    redisTemplate.delete(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                } else {
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
                }
            }
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + parentorderserialno);
        }

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Long couponUserId = goodsCouponInfoVO.getCouponUserId();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId);
                iteratorGoodsCouponInfo.remove();
            }

            redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno);
        }

        //拿出存在redis的订单商品短信息集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no)) {
            List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();
                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                if (count.equals(goodsCount)) {
                    redisTemplate.delete(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                } else {
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
                }
            }
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + out_trade_no);
        }

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Long couponUserId = goodsCouponInfoVO.getCouponUserId();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId);
                iteratorGoodsCouponInfo.remove();
            }

            redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);
        }

        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        //修改数据库支付/取消时间
        zlOrderMapper.updateOrderUpdateDate(out_trade_no, updateDate);
    }

    @Override
    public void autoCancelOrder(String out_trade_no) {

        //拿出存在redis的订单商品短信息集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no)) {
            List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();
                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                if (count.equals(goodsCount)) {
                    redisTemplate.delete(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                } else {
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
                }
            }
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + out_trade_no);
        }

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Long couponUserId = goodsCouponInfoVO.getCouponUserId();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId);
                iteratorGoodsCouponInfo.remove();
            }

            redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);
        }

        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        if (out_trade_no.startsWith("SM")) {
            //修改数据库支付/取消时间
            zlOrderMapper.updateAllOrderUpdateDate(out_trade_no, updateDate);
        } else {
            //修改数据库支付/取消时间
            zlOrderMapper.updateOrderUpdateDate(out_trade_no, updateDate);
        }
    }

    @Override
    public OrderStatusVO getByOrderSerialNo(String out_trade_no) {
        if (out_trade_no.startsWith("SM")) {
            OrderStatusVO orderStatusVO = zlOrderMapper.getByParentOrderSerialNo(out_trade_no);
            return orderStatusVO;
        } else {
            OrderStatusVO orderStatusVO = zlOrderMapper.getByOrderSerialNo(out_trade_no);
            return orderStatusVO;
        }
    }

    @Override
    public List<OrderPayShortInfoVO> getOrderByOrderSerialNo(String out_trade_no) {
        List<OrderPayShortInfoVO> orderPayShortInfoVOList = zlOrderMapper.getOrderByOrderSerialNo(out_trade_no);
        return orderPayShortInfoVOList;
    }

    @Override
    public void userDeleteOrder(String orderSerialNo, Integer belongModule) {
        zlOrderMapper.userDeleteOrder(orderSerialNo, belongModule);
        zlOrderDetailMapper.userDeleteOrder(orderSerialNo, belongModule);
    }

    @Override
    public Long waitForPayTotal(Long userId) {
        return zlOrderMapper.waitForPayTotal(userId);
    }

    @Override
    public Long waitForGoodsTotal(Long userId) {
        return zlOrderMapper.waitForGoodsTotal(userId);
    }

    @Override
    public Long allOrderTotal(Long userId) {
        return zlOrderMapper.allOrderTotal(userId);
    }

    @Override
    public void OrderNoticeToPhp(String out_trade_no) {

        ZlOrder zlOrder = new ZlOrder();
        if (out_trade_no.startsWith("SM")) {
            zlOrder.setParentorderserialno(out_trade_no);
            List<ZlOrder> zlOrderList = zlOrderMapper.select(zlOrder);
            if (CollectionUtils.isEmpty(zlOrderList)) {
                throw new BizException("参数有误...");
            }
            for (ZlOrder order : zlOrderList) {
                Long couponuserid = order.getCouponuserid();
                if (couponuserid != 0) {
                    zlCouponuserMapper.updateStatus(couponuserid);
                }
                sendOrderNoticeToPhp(order, order.getOrderserialno());
            }
        } else {
            zlOrder.setOrderserialno(out_trade_no);
            zlOrder = zlOrderMapper.selectOne(zlOrder);
            if (zlOrder == null) {
                throw new BizException("参数有误...");
            }
            Long couponuserid = zlOrder.getCouponuserid();
            if (couponuserid != 0) {
                zlCouponuserMapper.updateStatus(couponuserid);
            }
            sendOrderNoticeToPhp(zlOrder, out_trade_no);
        }
    }

    private void sendOrderNoticeToPhp(ZlOrder zlOrder, String out_trade_no) {
        ShopOrderToPhp shopOrderToPhp = new ShopOrderToPhp();

        shopOrderToPhp.setOrderID(zlOrder.getOrderid());
        shopOrderToPhp.setHotelID(zlOrder.getHotelid());
        shopOrderToPhp.setHotelName(zlOrder.getHotelname());
        shopOrderToPhp.setRoomID(zlOrder.getRoomid());
        shopOrderToPhp.setRoomNumber(zlOrder.getRoomnumber());
        shopOrderToPhp.setBelongModule(zlOrder.getBelongmodule());
        shopOrderToPhp.setTotalPrice(zlOrder.getTotalprice());
        shopOrderToPhp.setCreateDate(zlOrder.getCreatedate());
        shopOrderToPhp.setPayStatus(zlOrder.getPaystatus());
        shopOrderToPhp.setOrderStatus(zlOrder.getOrderstatus());
        shopOrderToPhp.setRefundStatus(zlOrder.getRefundstatus());
        shopOrderToPhp.setRemark(zlOrder.getRemark());

        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");

        if (out_trade_no.startsWith("BL")) {
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_STORE_ORDER);
        } else if (out_trade_no.startsWith("QQ")) {
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_SEXTOY_ORDER);
        } else if (out_trade_no.startsWith("TC")) {
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_LOCALSPECIALTY_ORDER);
        } else if (out_trade_no.startsWith("CY")) {
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_CATERING_ORDER);
        }
        orderPhpSendVO.setMessage(shopOrderToPhp);
        String channel = orderPhpSendVO.getChannel();
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(channel, orderStr);
        logger.info("推送商超订单到redis通知php后台人员完成,订单信息:" + orderPhpSendVO);
    }

    /**
     * 多模块提交订单后未支付后又吊起单模块来提交订单,删除redis原有的订单信息
     *
     * @param out_trade_no
     */
    private void deleteRedisAllOrder(String out_trade_no) {
        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderserialno(out_trade_no);
        zlOrder = zlOrderMapper.selectOne(zlOrder);

        if (zlOrder == null) {
            throw new BizException("订单编号有误,请重新填写");
        }
        String parentorderserialno = zlOrder.getParentorderserialno();

        //拿出存在redis的订单商品短信息集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno)) {
            List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno);
            for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();
                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                if (count.equals(goodsCount)) {
                    redisTemplate.delete(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                } else {
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
                }
            }
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + parentorderserialno);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + parentorderserialno);
        }

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Long couponUserId = goodsCouponInfoVO.getCouponUserId();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId);
                iteratorGoodsCouponInfo.remove();
            }

            redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentorderserialno);
        }
    }

    /**
     * 提交订单
     *
     * @param userID
     * @param hotelBasicVO
     * @param goodsInfoParamMap
     */
    private synchronized UserGoodsReturn submitManyOrder(HttpServletRequest request, Long userID, HotelBasicVO hotelBasicVO, Map<String, List<GoodsInfoParam>> goodsInfoParamMap, int orderFlag) {

        String ipAddr = IpUtils.getIpAddr(request);
        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userID);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        //封装商品短信息对象
        List<GoodsShortInfoVO> goodsShortInfoVOList = new LinkedList<>();

        //封装商品优惠券对象
        List<GoodsCouponInfoVO> goodsCouponInfoVOList = new LinkedList<>();

        //封装用户商品信息并返回
        UserGoodsReturn userGoodsReturn = new UserGoodsReturn();

        //查询该酒店的配送费
        BigDecimal sendCash = zlHotelDetailMapper.findSendCashByHotelID(hotelBasicVO.getHotelID());

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        //调用判断用户选择的商品是否库存充足
        List<GoodsStockCountNo> goodsStockCountNoList = judgeStock(hotelBasicVO, goodsInfoParamMap);
        if (goodsStockCountNoList.size() > 0) {
            //集合长度大于0,说明有库存不足的商品
            userGoodsReturn.setUserGoodsInfoList(goodsStockCountNoList);
            userGoodsReturn.setContent("返回的列表商品库存不足,请处理！");
            userGoodsReturn.setFlag(-1);
            return userGoodsReturn;
        }

        //封装订单信息列表
        List<ZlOrder> zlOrderList = new LinkedList<>();

        //封装父订单信息
        ZlOrderParent zlOrderParent = new ZlOrderParent();
        BigDecimal alltotalprice = new BigDecimal(0);//总价
        BigDecimal allactuallypay = new BigDecimal(0);//实际支付金额

        Set<String> keySet = goodsInfoParamMap.keySet();

        //调用工具类生成父订单编号
        String parentOrderSerialNo = OrderIDUtil.createOrderID("SM");
        zlOrderParent.setUserid(userID);
        zlOrderParent.setParentorderserialno(parentOrderSerialNo);
        zlOrderParent.setIsdelete(false);
        zlOrderParent.setCreatedate(currertTime);
        zlOrderParent.setUpdatedate(currertTime);

        for (String key : keySet) {
            //调用工具类生成订单编号
            String orderSerialNo = "";

            if ("1".equals(key)) {
                orderSerialNo = OrderIDUtil.createOrderID("BL");
            } else if ("2".equals(key)) {
                orderSerialNo = OrderIDUtil.createOrderID("CY");
            } else if ("3".equals(key)) {
                orderSerialNo = OrderIDUtil.createOrderID("QQ");
            } else if ("4".equals(key)) {
                orderSerialNo = OrderIDUtil.createOrderID("TC");
            }

            //计算各个模块总金额
            BigDecimal totalPrice = new BigDecimal(0);
            //封装订单信息
            ZlOrder zlOrder = new ZlOrder();

            zlOrder.setOperatorip(ipAddr);
            zlOrder.setOperatorname(zlWxuserdetail.getRealname());
            zlOrder.setUserid(userID);
            zlOrder.setParentorderserialno(parentOrderSerialNo);
            zlOrder.setHotelid(hotelBasicVO.getHotelID());
            zlOrder.setHotelname(hotelBasicVO.getHotelName());
            zlOrder.setRoomid(hotelBasicVO.getRoomID());
            zlOrder.setRoomnumber(hotelBasicVO.getRoomNumber());
            //获取每个模块类型的商品数据
            List<GoodsInfoParam> goodsInfoParamList = goodsInfoParamMap.get(key);
            //获取该模块类型商品的第一张商品图片地址
            String coverImgUrl = goodsInfoParamList.get(0).getCoverImgUrl();
            //获取该模块类型商品的类型
            Short belongModule = goodsInfoParamList.get(0).getBelongModule();

            //订单类型：1酒店订单，2供应商订单
            zlOrder.setOrdertype((byte) 1);

            //如果该模块是土特产,则需要判断是否需要配送
            if ("4".equals(key)) {
                String deliveryAddress = goodsInfoParamList.get(0).getDeliveryAddress();
                String consignee = goodsInfoParamList.get(0).getConsignee();
                String tel = goodsInfoParamList.get(0).getTel();
                //不为空说明需要配送
                if (deliveryAddress != null) {
                    //设置收货地址,收货人姓名和收货人联系电话
                    zlOrder.setDeliveryaddress(deliveryAddress);
                    zlOrder.setConsignee(consignee);
                    zlOrder.setTel(tel);
                }
                //土特产配送方式
                zlOrder.setDeliverytype(goodsInfoParamList.get(0).getDeliverytype());
                //订单类型：1酒店订单，2供应商订单
                zlOrder.setOrdertype((byte) 2);
            }

            //配送方式
            zlOrder.setDeliverytype(goodsInfoParamList.get(0).getDeliverytype());
            //供应商id
            Integer hotelGoodsSkuId = goodsInfoParamList.get(0).getHotelGoodsSkuId();
            Integer supplierID = zlOrderMapper.getsupplierID(hotelGoodsSkuId);
            zlOrder.setSupplierid(supplierID);

            //遍历每个模块类型的商品价格并相加
            for (int i = 0; i < goodsInfoParamList.size(); i++) {
                BigDecimal price = goodsInfoParamList.get(i).getPrice();
                BigDecimal goodsCount = BigDecimal.valueOf(goodsInfoParamList.get(i).getGoodsCount());
                totalPrice = totalPrice.add(price.multiply(goodsCount));
            }
            totalPrice = totalPrice.add(sendCash);
            zlOrder.setTotalprice(totalPrice);
            alltotalprice = alltotalprice.add(totalPrice);
            zlOrder.setSendcash(sendCash);

            //获取优惠券自增id
            Long couponUserId = goodsInfoParamList.get(0).getCouponUserId();
            //获取备注信息
            String remark = goodsInfoParamList.get(0).getRemark();

            //如果用户优惠券自增id不为-1,说明用户在该模块使用了优惠券
            if (couponUserId != -1) {
                //根据优惠券id查询优惠券信息
                CouponUserVO couponUserVO = zlCouponMapper.selectCouponInfo(couponUserId);

                if (couponUserVO == null) {
                    throw new BizException("优惠券有误!");
                }

                //封装优惠券对象,放入list集合存入redis
                GoodsCouponInfoVO goodsCouponInfoVO = new GoodsCouponInfoVO();
                goodsCouponInfoVO.setCouponUserId(couponUserId);
                goodsCouponInfoVO.setUserID(userID);
                goodsCouponInfoVO.setCouponID(couponUserVO.getCouponid());
                goodsCouponInfoVO.setBelongModule(belongModule);

                goodsCouponInfoVOList.add(goodsCouponInfoVO);

                //算出用户在这个模块的实际支付金额(优惠券类型 1：满减券 2:折扣券 3:代金券)
                if (couponUserVO.getType() == 1) {
                    BigDecimal actuallyPay = totalPrice.subtract(couponUserVO.getPrice());
                    if (actuallyPay.intValue() < 0.01) {
                        actuallyPay = new BigDecimal(0.00);
                    }
                    zlOrder.setActuallypay(actuallyPay);
                    allactuallypay = allactuallypay.add(actuallyPay);
                    zlOrder.setCouponcash(couponUserVO.getPrice());
                } else if (couponUserVO.getType() == 2) {
                    if (couponUserVO.getDiscount() > 10) {
                        throw new BizException("优惠券出错...");
                    }

                    BigDecimal actuallyPay = totalPrice.multiply(BigDecimal.valueOf(couponUserVO.getDiscount()).divide(BigDecimal.valueOf(10)));
                    if (actuallyPay.intValue() < 0.01) {
                        actuallyPay = new BigDecimal(0.00);
                    }
                    zlOrder.setActuallypay(actuallyPay);
                    allactuallypay = allactuallypay.add(actuallyPay);
                    zlOrder.setCouponcash(totalPrice.subtract(actuallyPay));
                } else if (couponUserVO.getType() == 3) {
                    BigDecimal actuallyPay = totalPrice.subtract((couponUserVO.getPricereplace()));
                    if (actuallyPay.intValue() < 0) {
                        actuallyPay = new BigDecimal(0.00);
                    }
                    zlOrder.setActuallypay(actuallyPay);
                    allactuallypay = allactuallypay.add(actuallyPay);
                    zlOrder.setCouponcash(couponUserVO.getPricereplace());
                }
                //将该优惠券放入redis,进行锁定,
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId, couponUserId);
                zlOrder.setCouponuserid(couponUserId);

            } else {
                //优惠券id为-1说明没有使用优惠券,实际支付金额=总价
                zlOrder.setActuallypay(totalPrice);
                allactuallypay = allactuallypay.add(totalPrice);
            }
            if (StringUtils.isNoneBlank(remark)) {
                //备注信息不为空且不是空串
                zlOrder.setRemark(remark);
            }

            zlOrder.setGoodscoverurl(coverImgUrl);
            zlOrder.setBelongmodule(belongModule);
            zlOrder.setPaystatus((byte) 1);
            zlOrder.setIsdelete(false);
            zlOrder.setIsuserdelete(false);
            zlOrder.setOrderstatus((byte) 0);
            zlOrder.setPaytype((byte) 1);
            zlOrder.setComeformid((byte) 1);
            zlOrder.setRefundstatus((byte) 0);
            zlOrder.setCreatedate(currertTime);
            //送达时间
            Integer deliveryDate = Math.toIntExact(goodsInfoParamList.get(0).getDeliveryDate() / 1000);
            zlOrder.setDeliverydate(deliveryDate);
            zlOrder.setUpdatedate(currertTime);
            zlOrder.setOrderserialno(orderSerialNo);

            //将订单数据存入数据库
            if (orderFlag != 2) {
                zlOrderMapper.insertSelective(zlOrder);
                zlOrderList.add(zlOrder);
            }

            for (int i = 0; i < goodsInfoParamList.size(); i++) {
                //封装订单详情信息
                ZlOrderDetail zlOrderDetail = new ZlOrderDetail();
                //获取用户选择的商品hotelGoodsSkuID和数量
                Integer hotelGoodsSkuID = goodsInfoParamList.get(i).getHotelGoodsSkuId();
                Integer goodsCount = goodsInfoParamList.get(i).getGoodsCount();

                //清空该用户在该酒店下的该商品的购物车信息
                List<UserCartVo> userCartVoList = zlCartMapper.findUserCart(hotelBasicVO.getHotelID(), userID);
                if (!CollectionUtils.isEmpty(userCartVoList)) {
                    List<AddCartParam> addCartParamLinkedList = new LinkedList<>();
                    Iterator<UserCartVo> userCartVoIterator = userCartVoList.iterator();
                    while (userCartVoIterator.hasNext()) {
                        UserCartVo userCartVo = userCartVoIterator.next();
                        Integer hgsId = userCartVo.getHotelGoodsSkuId();
                        if (hgsId.equals(hotelGoodsSkuID)) {
                            userCartVoIterator.remove();
                        }
                    }
                    if (!CollectionUtils.isEmpty(userCartVoList)) {
                        for (UserCartVo userCartVo : userCartVoList) {
                            AddCartParam addCartParam = new AddCartParam();
                            addCartParam.setHotelGoodsSkuId(userCartVo.getHotelGoodsSkuId());
                            addCartParam.setBelongModule(userCartVo.getBelongModule());
                            addCartParam.setGoodsCount(userCartVo.getGoodsCount());
                            addCartParamLinkedList.add(addCartParam);
                        }
                        // 先删除用户之前购物车数据
                        zlCartMapper.emptyUserCart(hotelBasicVO.getHotelID(), userID, 0);
                        // 添加新的购物车数据
                        zlCartMapper.addUserCartBatch(hotelBasicVO.getHotelID(), userID, addCartParamLinkedList, currertTime);
                    } else {
                        zlCartMapper.emptyUserCart(hotelBasicVO.getHotelID(), userID, 0);
                    }
                }

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
                zlOrderDetail.setGoodsname(goodsInfoParamList.get(i).getGoodsName());
                zlOrderDetail.setGoodscoverurl(goodsInfoParamList.get(i).getCoverImgUrl());
                zlOrderDetail.setPrice(goodsInfoParamList.get(i).getPrice());
                zlOrderDetail.setGoodscount(goodsInfoParamList.get(i).getGoodsCount());
                zlOrderDetail.setBelongmodule(goodsInfoParamList.get(i).getBelongModule());
                zlOrderDetail.setIsdelete(false);
                zlOrderDetail.setIsuserdelete(false);
                zlOrderDetail.setCreatedate(currertTime);
                zlOrderDetail.setOrderserialno(orderSerialNo);
                zlOrderDetail.setUpdatedate(currertTime);

                //封装订单商品短信息,并存入列表,准备放入redis
                GoodsShortInfoVO goodsShortInfoVO = new GoodsShortInfoVO();
                goodsShortInfoVO.setGoodsCount(goodsInfoParamList.get(i).getGoodsCount());
                goodsShortInfoVO.setHotelGoodsSkuID(hotelGoodsSkuID);
                goodsShortInfoVO.setBelongModule(goodsInfoParamList.get(i).getBelongModule());
                goodsShortInfoVO.setHotelID(hotelBasicVO.getHotelID());
                goodsShortInfoVO.setUserID(userID);
                goodsShortInfoVOList.add(goodsShortInfoVO);
                //将订单详情数据存入数据库
                if (orderFlag != 2) {
                    zlOrderDetailMapper.insert(zlOrderDetail);
                }
            }
        }

        zlOrderParent.setAlltotalprice(alltotalprice);
        zlOrderParent.setAllactuallypay(allactuallypay);

        if (orderFlag != 2) {
            zlOrderParentMapper.insertSelective(zlOrderParent);
            zlOrderMapper.updateParentOrderID(zlOrderParent.getParentid(), parentOrderSerialNo);
        }


        if (orderFlag == 0) {
            //将该订单商品放入redis,进行锁定
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + orderSerialNo, goodsShortInfoVOList);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + parentOrderSerialNo, goodsShortInfoVOList);
            //将该订单商品标记放入redis,时间最长为5分钟
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + orderSerialNo, goodsShortInfoVOList, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + parentOrderSerialNo, goodsShortInfoVOList, 5, TimeUnit.MINUTES);

            if (goodsCouponInfoVOList.size() > 0) {
                //将该订单优惠券集合放入redis
//            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + orderSerialNo, goodsCouponInfoVOList);
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + parentOrderSerialNo, goodsCouponInfoVOList);
            }
        } else if (orderFlag == 1) {
            ZlOrder zlOrder = new ZlOrder();
            zlOrder.setParentorderid(zlOrderParent.getParentid());
            zlOrder = zlOrderMapper.selectOne(zlOrder);
            if (zlOrder == null) {
                throw new BizException("订单生成有误,请重新提交");
            }
            //将该订单商品放入redis,进行锁定
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + orderSerialNo, goodsShortInfoVOList);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + zlOrder.getOrderserialno(), goodsShortInfoVOList);
            //将该订单商品标记放入redis,时间最长为5分钟
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + orderSerialNo, goodsShortInfoVOList, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + zlOrder.getOrderserialno(), goodsShortInfoVOList, 5, TimeUnit.MINUTES);

            if (goodsCouponInfoVOList.size() > 0) {
                //将该订单优惠券集合放入redis
//            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + orderSerialNo, goodsCouponInfoVOList);
                redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + zlOrder.getOrderserialno(), goodsCouponInfoVOList);

            }
        } else if (orderFlag == 2) {
            Short belongModule = -1;
            String orderSerialNo = "";
            for (String key : keySet) {
                List<GoodsInfoParam> goodsInfoParams = goodsInfoParamMap.get(key);
                belongModule = goodsInfoParams.get(0).getBelongModule();
                orderSerialNo = goodsInfoParams.get(0).getOrderSerialNo();
                break;
            }

            if (belongModule == -1) {
                throw new BizException("模块belongModule属性有误,请重新提交!");
            }
            if (!StringUtils.isNoneBlank(orderSerialNo)) {
                throw new BizException("订单编号属性有误,请重新提交!");
            }

            ZlOrder zlOrder = new ZlOrder();
            zlOrder.setOrderserialno(orderSerialNo);
            List<ZlOrder> orderList = zlOrderMapper.select(zlOrder);
            if (CollectionUtils.isEmpty(orderList)) {
                throw new BizException("订单提交有误,请重新提交!");
            }

            for (ZlOrder order : orderList) {
                if (belongModule.equals(order.getBelongmodule())) {
                    //将该订单商品放入redis,进行锁定
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + orderSerialNo, goodsShortInfoVOList);
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO + order.getOrderserialno(), goodsShortInfoVOList);
                    //将该订单商品标记放入redis,时间最长为5分钟
//        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + orderSerialNo, goodsShortInfoVOList, 5, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + order.getOrderserialno(), goodsShortInfoVOList, 5, TimeUnit.MINUTES);

                    if (goodsCouponInfoVOList.size() > 0) {
                        //将该订单优惠券集合放入redis
//            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + orderSerialNo, goodsCouponInfoVOList);
                        redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + order.getOrderserialno(), goodsCouponInfoVOList);

                    }
                    zlOrderList.add(order);
                    userGoodsReturn.setUserGoodsInfoList(zlOrderList);
                    userGoodsReturn.setContent("提交订单成功,请进行下单操作!");
                    userGoodsReturn.setFlag(0);
                    return userGoodsReturn;
                }
            }

        }

        userGoodsReturn.setUserGoodsInfoList(zlOrderList);
        userGoodsReturn.setContent("提交订单成功,请进行下单操作!");
        userGoodsReturn.setFlag(1);
        return userGoodsReturn;

    }

    @Override
    public void orderRefundRecord(HttpServletRequest request, Long userID, RefundRecordDTO refundRecordDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(refundRecordDTO.getOrderid());
        zlOrder = zlOrderMapper.selectOne(zlOrder);

        if (zlOrder == null) {
            throw new BizException("订单id有误,请重新输入...");
        }

        if (zlOrder.getBelongmodule() == 4) {
            if (refundRecordDTO.getRefundtype() == null) {
                throw new BizException("售后类型不能为空");
            }
            if (refundRecordDTO.getGoodsstatus() == null) {
                throw new BizException("订单商品状态不能为空");
            }
            if (refundRecordDTO.getRefundtype() != 1 && refundRecordDTO.getRefundtype() != 2) {
                throw new BizException("售后类型有误,请重新填写...");
            }
            if (refundRecordDTO.getGoodsstatus() != 1 && refundRecordDTO.getGoodsstatus() != 2) {
                throw new BizException("订单商品状态有误,请重新填写...");
            }
        }

        zlOrderMapper.updateOrderRefundInfo(refundRecordDTO.getOrderid(), currertTime);

        BigDecimal actuallypay = zlOrder.getActuallypay();

        if (!(actuallypay.subtract(refundRecordDTO.getRefundmoney()).intValue() == 0)) {
            throw new BizException("退款金额有误,请重新填写...");
        }

        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userID);
        zlWxuser = zlWxuserMapper.selectOne(zlWxuser);
        if (zlWxuser == null) {
            throw new BizException("用户id有误!");
        }

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        if (StringUtils.isNoneBlank(refundRecordDTO.getUserremark())) {
            zlRefundRecord.setUserremark(refundRecordDTO.getUserremark());
        }
        if (StringUtils.isNoneBlank(refundRecordDTO.getRefundgoodsurls())) {
            zlRefundRecord.setRefundgoodsurls(refundRecordDTO.getRefundgoodsurls());
        }

        //调用工具类生成退款订单编号
        String orderserialno = zlOrder.getOrderserialno();
        String refundSerialNo = OrderIDUtil.createOrderID(orderserialno.substring(0, 2));

        zlRefundRecord.setOrderid(refundRecordDTO.getOrderid());//订单ID
        zlRefundRecord.setRefundserialno(refundSerialNo);//退款单号
        zlRefundRecord.setUserid(userID);//用户ID
        zlRefundRecord.setUsername(zlWxuser.getNickname());
        zlRefundRecord.setRefundmoney(refundRecordDTO.getRefundmoney());//退款金额
        zlRefundRecord.setRecordstatus((byte) 1);//退款状态
        zlRefundRecord.setChecktype((byte) 1);//审核类型:1-B端(供应商/酒店);2-S端(平台审核)
        zlRefundRecord.setRefundtype(refundRecordDTO.getRefundtype());//退款类型
        zlRefundRecord.setCreatedate(currertTime);//创建时间
        zlRefundRecord.setUpdatedate(currertTime);//修改时间
        zlRefundRecord.setGoodsstatus(refundRecordDTO.getGoodsstatus());
        zlRefundRecordMapper.insertSelective(zlRefundRecord);

        ZlRefundCheckRecord zlRefundCheckRecord = new ZlRefundCheckRecord();
        zlRefundCheckRecord.setOrderid(refundRecordDTO.getOrderid());//订单Id
        zlRefundCheckRecord.setRefundrecid(zlRefundRecord.getRecid());//退款申请记录
        zlRefundCheckRecord.setChecktype((byte) 1);//审核类型:1-B端(供应商/酒店);2-S端(平台审核)
        zlRefundCheckRecord.setCheckstatus((byte) 1);//退款状态
        zlRefundCheckRecord.setCurrentchecktype((byte) 3);//当前发起/处理类型:1-B端(供应商/酒店);2-S端(平台);3-用户
        zlRefundCheckRecord.setUseroperatype((byte) 1);//-2用户取消申述,-1用户取消申请,0非用户操作,1用户发起申请,2用户发起申述,3用户上传物流信息
        if (StringUtils.isNoneBlank(refundRecordDTO.getUserremark())) {
            zlRefundCheckRecord.setOperatorremark(refundRecordDTO.getUserremark());
        }

        String ipAddr = IPUtils.getIpAddr(request);

        zlRefundCheckRecord.setOperatorip(ipAddr);
        zlRefundCheckRecord.setOperatorname(zlWxuser.getNickname());
        zlRefundCheckRecord.setCreatedate(currertTime);//创建时间
        zlRefundCheckRecord.setUpdatedate(currertTime);//修改时间
        zlRefundCheckRecordMapper.insertSelective(zlRefundCheckRecord);
        zlOrderMapper.updateRefundStatus(refundRecordDTO.getOrderid(), currertTime);

//        RefundapplyInfoVO refundapplyInfoVO = new RefundapplyInfoVO();
//        if ((zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 1) || (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 5)) {
//            refundapplyInfoVO.setRefundapplyInfoStatus(1);
//            refundapplyInfoVO.setContent("退款申请提交成功,请直接申请退款!");
//        } else if (((zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 2) || (zlOrder.getPaystatus() == 2 && zlOrder.getOrderstatus() == 3))) {
//            refundapplyInfoVO.setRefundapplyInfoStatus(2);
//            refundapplyInfoVO.setContent("退款申请提交成功,请等待审核!");
//        }

//        return refundapplyInfoVO;

    }

    @Override
    public void cancelRefundapply(Long orderId) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

//        ZlOrder zlOrder = zlOrderMapper.getOrderByOrderId(orderId);
        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(orderId);
        zlOrder = zlOrderMapper.selectOne(zlOrder);
        if (zlOrder == null) {
            throw new BizException("订单id有误,请重新输入!");
        }

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(orderId);
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
        if (zlRefundRecord == null) {
            throw new BizException("该订单没有退款申请记录!");
        }

        ZlRefundCheckRecord zlRefundCheckRecord = new ZlRefundCheckRecord();
        zlRefundCheckRecord.setOrderid(orderId);
        zlRefundCheckRecord.setRefundrecid(zlRefundRecord.getRecid());
        zlRefundCheckRecord.setChecktype((byte) 3);
        zlRefundCheckRecord.setCheckstatus((byte) -1);
        zlRefundCheckRecord.setCreatedate(currertTime);
        zlRefundCheckRecord.setUpdatedate(currertTime);
        zlRefundCheckRecord.setUseroperatype((byte) -1);//-2用户取消申述,-1用户取消申请,0非用户操作,1用户发起申请,2用户发起申述,3用户上传物流信息

        if (zlOrder.getOrderstatus() == 1 || zlOrder.getOrderstatus() == 2 || zlOrder.getOrderstatus() == 3) {
            if (zlOrder.getRefundstatus() == 1) {
                //修改数据库取消退款的信息
                zlOrderMapper.updateRefundStatus2(orderId, currertTime);
                zlRefundRecordMapper.updateRefundStatus2(orderId, currertTime);
                zlRefundCheckRecordMapper.insertSelective(zlRefundCheckRecord);
            } else {
                throw new BizException("该订单状态不允许取消退款!");
            }
        } else {
            throw new BizException("该订单状态不允许取消退款!");
        }

    }

    @Override
    public void uploadExpressInfo(HttpServletRequest request, UploadExpressInfoDTO uploadExpressInfoDTO) {

        String ipAddr = IPUtils.getIpAddr(request);
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userId);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(uploadExpressInfoDTO.getOrderid());
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
        if (zlRefundRecord == null) {
            throw new BizException("订单id有误,请重新输入!");
        }

        zlOrderMapper.updateRefundStatus5(uploadExpressInfoDTO.getOrderid(), currertTime);
        zlRefundRecordMapper.uploadExpressInfo(uploadExpressInfoDTO.getOrderid(), uploadExpressInfoDTO.getExpressid(), uploadExpressInfoDTO.getUsertracknumber(), currertTime);

        ZlRefundCheckRecord zlRefundCheckRecord = new ZlRefundCheckRecord();
        zlRefundCheckRecord.setRefundrecid(zlRefundRecord.getRecid()); //申请记录ID
        zlRefundCheckRecord.setOrderid(zlRefundRecord.getOrderid()); //订单ID
        zlRefundCheckRecord.setChecktype((byte) 3); //审核类型:1-B端(供应商/酒店);2-S端(平台审核);
        zlRefundCheckRecord.setCheckstatus((byte) 1); //退款状态:-12退款关闭(不可再申述);-2退款被驳回;-1用户取消退款;1审核中;2审核通过;3退款中(退款发起);4已退款
        zlRefundCheckRecord.setOperatorname(zlWxuserdetail.getRealname());
        zlRefundCheckRecord.setOperatorip(ipAddr);
        zlRefundCheckRecord.setCreatedate(currertTime);
        zlRefundCheckRecord.setUpdatedate(currertTime);
        zlRefundCheckRecord.setCurrentchecktype((byte) 3);
        zlRefundCheckRecord.setUseroperatype((byte) 3);
        if (StringUtils.isNoneBlank(uploadExpressInfoDTO.getOperatorremark())) {
            zlRefundCheckRecord.setOperatorremark(uploadExpressInfoDTO.getOperatorremark());
        }

        zlRefundCheckRecordMapper.insertSelective(zlRefundCheckRecord);

    }

    @Override
    public List<ZlExpress> getExpressList() {
        List<ZlExpress> zlExpressList = zlExpressMapper.getExpressList();
        return zlExpressList;
    }

    @Override
    public BigDecimal getOrderActuallyPay(String out_trade_no) {
        BigDecimal orderActuallyPay = zlOrderMapper.getOrderActuallyPay(out_trade_no);
        return orderActuallyPay;
    }

    @Override
    public BigDecimal getOrderActuallyPay2(String out_trade_no) {
        BigDecimal orderActuallyPay = zlOrderMapper.getOrderActuallyPay2(out_trade_no);
        return orderActuallyPay;
    }

    @Override
    public void orderRefundAppeal(HttpServletRequest request, Long userID, OrderRefundAppealDTO orderRefundAppealDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlOrder zlOrder = new ZlOrder();
        zlOrder.setOrderid(orderRefundAppealDTO.getOrderid());
        zlOrder = zlOrderMapper.selectOne(zlOrder);
        if (zlOrder == null) {
            throw new BizException("订单id有误,请重新输入!");
        }

        ZlRefundRecord zlRefundRecord = new ZlRefundRecord();
        zlRefundRecord.setOrderid(orderRefundAppealDTO.getOrderid());
        zlRefundRecord = zlRefundRecordMapper.selectOne(zlRefundRecord);
        if (zlRefundRecord == null) {
            throw new BizException("该订单没有退款申请记录,无法申诉!");
        }

        if (zlRefundRecord.getRecordstatus() != -2) {
            throw new BizException("该订单不允许申诉!");
        }

        ZlRefundCheckRecord zlRefundCheckRecord = new ZlRefundCheckRecord();
        if (zlRefundRecord.getIsusersend()) {
            //修改数据库该订单状态为B端审核通过,S端审核中
            zlOrderMapper.updateRefundStatus4Two(orderRefundAppealDTO.getOrderid(), currertTime);
            zlRefundRecordMapper.updateRefundStatus4Two(orderRefundAppealDTO.getOrderid(), currertTime);
            zlRefundCheckRecord.setCheckstatus((byte) 2);//审核类型:1-B端(供应商/酒店);2-S端(平台审核)
            zlRefundCheckRecord.setCurrentchecktype((byte) 3);//当前发起/处理类型:1-B端(供应商/酒店);2-S端(平台);3-用户

        } else {
            //修改数据库该订单状态为审核中
            zlOrderMapper.updateRefundStatus4(orderRefundAppealDTO.getOrderid(), currertTime);
            zlRefundRecordMapper.updateRefundStatus4(orderRefundAppealDTO.getOrderid(), currertTime);
            zlRefundCheckRecord.setCheckstatus((byte) 1);//退款状态
            zlRefundCheckRecord.setCurrentchecktype((byte) 3);//当前发起/处理类型:1-B端(供应商/酒店);2-S端(平台);3-用户
        }


        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userID);
        zlWxuser = zlWxuserMapper.selectOne(zlWxuser);
        if (zlWxuser == null) {
            throw new BizException("用户id有误!");
        }

        String ipAddr = IPUtils.getIpAddr(request);//获取ip
        zlRefundCheckRecord.setOperatorip(ipAddr);
        zlRefundCheckRecord.setOperatorname(zlWxuser.getNickname());
        zlRefundCheckRecord.setOrderid(orderRefundAppealDTO.getOrderid());
        zlRefundCheckRecord.setRefundrecid(zlRefundRecord.getRecid());
        zlRefundCheckRecord.setChecktype((byte) 2);//审核类型:1-B端(供应商/酒店);2-S端(平台审核)
        zlRefundCheckRecord.setOperatorremark(orderRefundAppealDTO.getAppealcontent());
        zlRefundCheckRecord.setCreatedate(currertTime);
        zlRefundCheckRecord.setUpdatedate(currertTime);
        zlRefundCheckRecord.setUseroperatype((byte) 2);//-2用户取消申述,-1用户取消申请,0非用户操作,1用户发起申请,2用户发起申述,3用户上传物流信息

        zlRefundCheckRecordMapper.insertSelective(zlRefundCheckRecord);
    }

    @Override
    public List<OrderRefundHistoryVO> orderRefundHistory(Long orderId) {
        List<OrderRefundHistoryVO> orderRefundHistoryVOList = zlRefundCheckRecordMapper.selectZlRefundCheckRecordList(orderId);
        if (CollectionUtils.isEmpty(orderRefundHistoryVOList)) {
            logger.info("该订单退款历史为空,订单id为" + orderId);
            return null;
        }
        ZlSupplierAddress zlSupplierAddress = zlSupplierAddresMapper.getSupplierAddress(orderId);
        if (zlSupplierAddress == null) {
            throw new BizException("参数有误!");
        }
        orderRefundHistoryVOList.get(0).setSupplierid(zlSupplierAddress.getSupplierid());
        orderRefundHistoryVOList.get(0).setAddress(zlSupplierAddress.getAddress());

        List<String> imageurllist = new LinkedList<>();
        if (StringUtils.isNoneBlank(orderRefundHistoryVOList.get(0).getRefundgoodsurls())) {
            String imageurls = orderRefundHistoryVOList.get(0).getRefundgoodsurls();
            if (imageurls.contains("|")) {
                String[] imageurlArr = imageurls.split("\\|");
                for (int i = 0; i < imageurlArr.length; i++) {
                    imageurllist.add(imageurlArr[i]);
                }
            } else {
                imageurllist.add(imageurls);
            }
        }
        orderRefundHistoryVOList.get(0).setImageurllist(imageurllist);

        for (OrderRefundHistoryVO orderRefundHistoryVO : orderRefundHistoryVOList) {
            if (orderRefundHistoryVO.getUseroperatype() == 3) {
                ZlExpress zlExpress = new ZlExpress();
                zlExpress.setExpressid(orderRefundHistoryVO.getUserexpressid());
                zlExpress.setIsdelete(false);
                zlExpress = zlExpressMapper.selectOne(zlExpress);
                if (zlExpress == null) {
                    throw new BizException("快递id有误!");
                }
                orderRefundHistoryVO.setExpressname(zlExpress.getExpressname());
            }
            if (orderRefundHistoryVO.getCurrentchecktype() != 3) {
                orderRefundHistoryVO.setHeadimgurl("");
            }
        }

        return orderRefundHistoryVOList;
    }

    @Override
    public ZlSupplierAddress getSupplierAddress(Long orderId) {
        ZlSupplierAddress zlSupplierAddress = zlSupplierAddresMapper.getSupplierAddress(orderId);
        if (zlSupplierAddress == null) {
            throw new BizException("参数有误!");
        }
        return zlSupplierAddress;
    }

}


