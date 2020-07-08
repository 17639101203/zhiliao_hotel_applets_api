package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderVO;
import com.zhiliao.hotel.mapper.ZlRentCarGoodsMapper;
import com.zhiliao.hotel.mapper.ZlRentCarOrderMapper;
import com.zhiliao.hotel.model.ZlRentCarGoods;
import com.zhiliao.hotel.model.ZlRentCarOrder;
import com.zhiliao.hotel.service.ZlRentCarGoodsService;
import com.zhiliao.hotel.utils.OrderIDUtil;
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
        String orderSerialNo = OrderIDUtil.createOrderID("");
        rentCarOrder.setOrderserialno(orderSerialNo);
        rentCarOrder.setOrderstatus((byte) 0);
        rentCarOrder.setGivebackdate(rentCarOrder.getRentenddate());
        rentCarOrder.setCancancelorderminute(rentCarGoods.getCancancelorderminute());
        rentCarOrder.setIsdelete(false);
        rentCarOrder.setIsuserdelete(false);
        rentCarOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        rentCarOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        int num = rentCarOrderMapper.insertSelective(rentCarOrder);

        // 推送消息
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        OrderPhpVO orderPhpVO = new OrderPhpVO();
        orderPhpVO.setOrderID(rentCarOrder.getOrderid());
        orderPhpVO.setSerialNumber(orderSerialNo);
        orderPhpVO.setHotelID(rentCarOrder.getHotelid());
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_RENT_CAR);
        orderPhpSendVO.setMessage(orderPhpVO);
        String orderStr = JSON.toJSONString(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_RENT_CAR, orderStr);

        if (num > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", rentCarOrder.getOrderid());
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
