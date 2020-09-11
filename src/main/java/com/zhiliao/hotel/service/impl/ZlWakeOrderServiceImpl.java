package com.zhiliao.hotel.service.impl;

import com.google.gson.Gson;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlWakeOrderDTO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.wake.vo.ZlWakeOrderToPhpVO;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlWakeOrderMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlWakeOrder;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.OrderLogService;
import com.zhiliao.hotel.service.ZlWakeOrderService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Autowired
    private OrderLogService orderLogService;

    /**
     * 提交叫醒订单
     *
     * @param userId
     * @param wakeOrder
     */
    @Override
    public Map<String, Object> addWakeOrder(Long userId, ZlWakeOrder wakeOrder) {
        //获取用户真实姓名和手机号
        ZlWxuserdetail wxuserdetail = new ZlWxuserdetail();
        wxuserdetail.setUserid(userId);
        ZlWxuserdetail zlWxuserdetail = wxuserdetailMapper.selectOne(wxuserdetail);

        String orderSerialNo = OrderIDUtil.createOrderID("JX");
        wakeOrder.setUsername(zlWxuserdetail.getRealname());
        if (StringUtils.isNoneBlank(zlWxuserdetail.getTel())) {
            zlWxuserdetail.setTel(zlWxuserdetail.getTel());
        }
        wakeOrder.setUserid(userId);
        wakeOrder.setOrderserialno(orderSerialNo);
        wakeOrder.setOrderstatus((byte) 0);
        wakeOrder.setIsdelete(false);
        wakeOrder.setIsuserdelete(false);
        wakeOrder.setComeformid(1);
        wakeOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(wakeOrder.getRoomnumber(), wakeOrder.getHotelid());
        if (zlHotelroom == null) {
            throw new BizException("您的码是前台码，不提供该服务");
        }
        wakeOrder.setFloornumber(zlHotelroom.getRoomfloor());

        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userId);
        ZlWxuser zw = zlWxuserMapper.selectOne(zlWxuser);
        wakeOrder.setUsername(zw.getNickname());

        try {
            wakeOrderMapper.insertSelective(wakeOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("提交失败!");
        }
        logger.info("叫醒订单插入数据库完成,订单id:" + wakeOrder.getOrderid());

        // 推送消息
        ZlWakeOrderToPhpVO zlWakeOrderToPhpVO = wakeOrderMapper.selectToPhp(wakeOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_WAKE, zlWakeOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_WAKE_ORDER);
        orderPhpSendVO.setMessage(zlWakeOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_WAKE_ORDER, orderStr);
        logger.info("推送叫醒订单到redis通知php后台人员完成,订单信息:" + zlWakeOrderToPhpVO);

        Map<String, Object> map = new HashMap<>();
        map.put("orderid", wakeOrder.getOrderid());
        map.put("serialnumber", orderSerialNo);
        return map;
    }

    /**
     * 订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public ZlWakeOrderDTO wakeOrderDetail(Long orderID) {
        return wakeOrderMapper.wakeOrderDetail2(orderID);
    }

    /**
     * 取消订单
     *
     * @param orderID
     */
    @Override
    public void cancelWakeOrder(Long orderID) {
        ZlWakeOrder wakeOrder = wakeOrderMapper.wakeOrderDetail(orderID);
        if (wakeOrder == null) {
            throw new BizException("参数有误!");
        }
        //将订单取消操作写到记录表中
        orderLogService.cancelOrderLog(wakeOrder.getOrderid(), wakeOrder.getHotelid(), wakeOrder.getCreatedate(), wakeOrder.getMoldtype());
        wakeOrderMapper.cancelWakeOrder(orderID, Math.toIntExact(System.currentTimeMillis() / 1000));
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
