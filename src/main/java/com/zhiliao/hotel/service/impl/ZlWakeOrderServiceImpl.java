package com.zhiliao.hotel.service.impl;

import com.google.gson.Gson;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.wake.vo.ZlWakeOrderToPhpVO;
import com.zhiliao.hotel.mapper.ZlWakeOrderMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlWakeOrder;
import com.zhiliao.hotel.service.ZlWakeOrderService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-05 15:47
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlWakeOrderServiceImpl implements ZlWakeOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ZlWakeOrderServiceImpl.class);

    @Autowired
    private ZlWakeOrderMapper wakeOrderMapper;
    @Autowired
    private ZlWxuserdetailMapper wxuserdetailMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 提交叫醒订单
     *
     * @param userId
     * @param wakeOrder
     */
    @Override
    public Map<String, Object> addWakeOrder(Long userId, ZlWakeOrder wakeOrder) {
        //获取用户真实姓名和手机号
//        ZlWxuserdetail wxuserdetail = new ZlWxuserdetail();
//        wxuserdetail.setUserid(userId);
//        ZlWxuserdetail zlWxuserdetail = wxuserdetailMapper.selectOne(wxuserdetail);
//        if (zlWxuserdetail == null) {
//            new RuntimeException("没有该用户信息");
//        }
        String orderSerialNo = OrderIDUtil.createOrderID("JX");
//        wakeOrder.setUsername(zlWxuserdetail.getRealname());
//        wakeOrder.setTel(zlWxuserdetail.getTel());
        wakeOrder.setUserid(userId);
        wakeOrder.setOrderserialno(orderSerialNo);
        wakeOrder.setOrderstatus((byte) 0);
        wakeOrder.setIsdelete(false);
        wakeOrder.setIsuserdelete(false);
        wakeOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        wakeOrderMapper.insertSelective(wakeOrder);
        logger.info("叫醒订单插入数据库完成,订单id:" + wakeOrder.getOrderid());

        // 推送消息
        ZlWakeOrderToPhpVO zlWakeOrderToPhpVO = wakeOrderMapper.selectToPhp(wakeOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_WAKE, zlWakeOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_WAKE);
        orderPhpSendVO.setMessage(zlWakeOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_WAKE, orderStr);
        logger.info("推送叫醒订单到redis通知php后台人员完成,订单信息:" + zlWakeOrderToPhpVO);

        Map<String, Object> map = new HashMap<>();
        map.put("orderID", wakeOrder.getOrderid());
        return map;
    }

    /**
     * 订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public ZlWakeOrder wakeOrderDetail(Long orderID) {
        return wakeOrderMapper.wakeOrderDetail(orderID);
    }

    /**
     * 取消订单
     *
     * @param orderID
     */
    @Override
    public void cancelWakeOrder(Long orderID) {
        ZlWakeOrder wakeOrder = wakeOrderMapper.wakeOrderDetail(orderID);
        if (wakeOrder != null) {
            wakeOrder.setOrderstatus((byte) -1);
            wakeOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            int num = wakeOrderMapper.updateById(wakeOrder);
            if (num == 0) {
                throw new RuntimeException("取消失败");
            }
        }

    }

    /**
     * 用户删除订单
     *
     * @param orderID
     */
    @Override
    public void dlWakeOrder(Long orderID) {
        ZlWakeOrder wakeOrder = wakeOrderMapper.wakeOrderDetail(orderID);
        if (wakeOrder == null) {
            throw new RuntimeException("此订单不存在");
        }
        if (wakeOrder.getOrderstatus() == -1 || wakeOrder.getOrderstatus() == 1) {
            wakeOrder.setIsuserdelete(true);
            wakeOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            int num = wakeOrderMapper.updateById(wakeOrder);
            if (num == 0) {
                throw new RuntimeException("删除失败");
            }
        } else {
            throw new RuntimeException("此订单待处理订单,请先取消订单再删除");
        }
    }
}
