package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderToPhpVO;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderVO;
import com.zhiliao.hotel.mapper.ZlRentCarGoodsMapper;
import com.zhiliao.hotel.mapper.ZlRentCarOrderMapper;
import com.zhiliao.hotel.model.ZlRentCarGoods;
import com.zhiliao.hotel.model.ZlRentCarOrder;
import com.zhiliao.hotel.service.ZlRentCarGoodsService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.PushInfoToPhpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 09:37
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlRentCarGoodsServiceImpl implements ZlRentCarGoodsService {

    private static final Logger logger = LoggerFactory.getLogger(ZlRentCarGoodsServiceImpl.class);

    @Autowired
    private ZlRentCarGoodsMapper rentCarGoodsMapper;

    @Autowired
    private ZlRentCarOrderMapper rentCarOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取车型列表
     * @param hotelid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult carGoodsList(Integer hotelid, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlRentCarGoods> result = rentCarGoodsMapper.carGoodsList(hotelid);
        PageInfo<ZlRentCarGoods> pageInfo = new PageInfo<>(result);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 车型商品详情
     *
     * @param goodsid
     * @return
     */
    @Override
    public ZlRentCarGoods rentCarDetail(Integer goodsid) {
        return rentCarGoodsMapper.rentCarDetail(goodsid);
    }

    /**
     * 提交订单
     *
     * @param userId
     * @param rentCarOrder
     * @return
     */
    @Override
    public Map<String, Object> addRentCar(Long userId, ZlRentCarOrder rentCarOrder, Integer goodsid) {

        rentCarOrder.setUserid(userId);

        //判断该车型数量充足
        ZlRentCarGoods rentCarGoods = rentCarGoodsMapper.rentCarDetail(goodsid);
        if (rentCarGoods == null) {
            throw new RuntimeException("该车型已被预定完,请选择其他车型");
        }
        if (rentCarGoods.getStockcount() <= 0) {
            throw new RuntimeException("该车型已被预定完,请选择其他车型");
        }
        /*ZlRentCarOrder order = rentCarOrderMapper.findAllByCarNumber(rentCarOrder.getHotelid(),rentCarOrder.getCarnumber());
        if (order != null){
        }*/
        String orderSerialNo = OrderIDUtil.createOrderID("ZC");
        rentCarOrder.setOrderserialno(orderSerialNo);
        rentCarOrder.setOrderstatus((byte) 0);
        rentCarOrder.setGoodsid(goodsid);
        rentCarOrder.setGivebackdate(rentCarOrder.getRentenddate());
        rentCarOrder.setCancancelorderminute(rentCarGoods.getCancancelorderminute());
        rentCarOrder.setIsdelete(false);
        rentCarOrder.setIsuserdelete(false);
        rentCarOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        rentCarOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        int num = rentCarOrderMapper.insertSelective(rentCarOrder);
        if (num > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", rentCarOrder.getOrderid());
            logger.info("租车服务订单插入数据库完成,订单id:" + rentCarOrder.getOrderid());

            // 推送消息
            RentCarOrderToPhpVO rentCarOrderToPhpVO = rentCarOrderMapper.selectToPhp(rentCarOrder.getOrderid());
//            PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_RENT_CAR, rentCarOrderToPhpVO);
            OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
            orderPhpSendVO.setForm("java");
            orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_RENT_CAR);
            orderPhpSendVO.setMessage(rentCarOrderToPhpVO);
            Gson gson = new Gson();
            String orderStr = gson.toJson(orderPhpSendVO);
            stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_RENT_CAR, orderStr);
            logger.info("推送租车服务订单到redis通知php后台人员完成,订单信息:" + rentCarOrderToPhpVO);
            return map;
        } else {
            throw new RuntimeException("提交失败");
        }
    }

    /**
     * 获取租车订单详情
     *
     * @param orderid
     * @return
     */
    @Override
    public RentCarOrderVO rentCarOrderDetail(Long orderid) {
        return rentCarOrderMapper.rentCarOrderDetailVO(orderid);
    }

    @Override
    public void cancelRentCarOrder(Long orderid) {
        ZlRentCarOrder rentCarOrder = rentCarOrderMapper.rentCarOrderDetail(orderid);
        if (rentCarOrder == null) {
            throw new RuntimeException("没有此订单");
        }
        rentCarOrder.setOrderstatus((byte) -1);
        rentCarOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        int num = rentCarOrderMapper.updateById(rentCarOrder);
        if (num == 0) {
            throw new RuntimeException("取消失败");

        }
    }

    /**
     * 用户删除订单
     *
     * @param orderid
     */
    @Override
    public void dlRentCarOrder(Long orderid) {
        ZlRentCarOrder rentCarOrder = rentCarOrderMapper.rentCarOrderDetail(orderid);
        if (rentCarOrder == null) {
            throw new RuntimeException("没有此订单");
        }
        rentCarOrder.setIsuserdelete(true);
        rentCarOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        int num = rentCarOrderMapper.dlRentCarOrder(rentCarOrder);
        if (num == 0) {
            throw new RuntimeException("取消失败");

        }
    }
}
