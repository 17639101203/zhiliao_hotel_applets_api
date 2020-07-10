package com.zhiliao.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.hotellive.param.ZlCheckoutOrderParam;
import com.zhiliao.hotel.controller.hotellive.param.ZlContinueLiveOrderParam;
import com.zhiliao.hotel.controller.hotellive.vo.ZlCheckoutOrderToPhpVO;
import com.zhiliao.hotel.controller.hotellive.vo.ZlContinueLiveOrderToPhpVO;
import com.zhiliao.hotel.controller.myOrder.vo.HotelBasicVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.mapper.ZlCheckoutOrderMapper;
import com.zhiliao.hotel.mapper.ZlContinueLiveOrderMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.ZlCheckoutOrder;
import com.zhiliao.hotel.model.ZlContinueLiveOrder;
import com.zhiliao.hotel.model.ZlWxuserdetail;
import com.zhiliao.hotel.service.HotelLiveOrderService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.PushInfoToPhpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-06 15:37
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class HotelLiveOrderServiceImpl implements HotelLiveOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ZlHotelFacilityServiceImpl.class);

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    private ZlCheckoutOrderMapper zlCheckoutOrderMapper;

    @Autowired
    private ZlContinueLiveOrderMapper zlContinueLiveOrderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Map<String, Object> checkoutOrder(Long userID, HotelBasicVO hotelBasicVO, ZlCheckoutOrderParam zlCheckoutOrderParam) {

        //调用工具类生成订单编号
        String orderSerialNo = OrderIDUtil.createOrderID("TF");

//        ZlWxuserdetail zlWxuserdetail = zlWxuserdetailMapper.findByUserId(userID);
        ZlCheckoutOrder zlCheckoutOrder = new ZlCheckoutOrder();
        zlCheckoutOrder.setUserid(userID);
        zlCheckoutOrder.setOrderserialno(orderSerialNo);
        zlCheckoutOrder.setHotelid(hotelBasicVO.getHotelID());
        zlCheckoutOrder.setHotelname(hotelBasicVO.getHotelName());
        zlCheckoutOrder.setRoomid(hotelBasicVO.getRoomID());
        zlCheckoutOrder.setRoomnumber(hotelBasicVO.getRoomNumber());
//        zlCheckoutOrder.setUsername(zlWxuserdetail.getRealname());
        zlCheckoutOrder.setRemark(zlCheckoutOrderParam.getRemark());
        zlCheckoutOrder.setTel(zlCheckoutOrder.getTel());
        zlCheckoutOrder.setOrderstatus((byte) 0);
        zlCheckoutOrder.setIsdelete(false);
        zlCheckoutOrder.setIsuserdelete(false);
        zlCheckoutOrder.setCheckoutdate((int) (zlCheckoutOrderParam.getCheckOutDate() / 1000));
        zlCheckoutOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        zlCheckoutOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        zlCheckoutOrderMapper.insertSelective(zlCheckoutOrder);
        logger.info("退房订单插入数据库完成,订单id:" + zlCheckoutOrder.getOrderid());

        // 推送消息
        ZlCheckoutOrderToPhpVO zlCheckoutOrderToPhpVO = zlCheckoutOrderMapper.selectToPhp(zlCheckoutOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_FACILITY, zlCheckoutOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_FACILITY);
        orderPhpSendVO.setMessage(zlCheckoutOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_FACILITY, orderStr);
        logger.info("推送退房订单到redis通知php后台人员完成,订单信息:" + zlCheckoutOrderToPhpVO);

        Map<String, Object> map = new HashMap<>();
        map.put("orderid", zlCheckoutOrder.getOrderid());
        return map;
    }

    @Override
    public Map<String, Object> continueLiveOrder(Long userID, HotelBasicVO hotelBasicVO, ZlContinueLiveOrderParam zlContinueLiveOrderParam) {
        //调用工具类生成订单编号
        String orderSerialNo = OrderIDUtil.createOrderID("XZ");

        ZlWxuserdetail zlWxuserdetail = zlWxuserdetailMapper.findByUserId(userID);
        ZlContinueLiveOrder zlContinueLiveOrder = new ZlContinueLiveOrder();
        zlContinueLiveOrder.setUserid(userID);
        zlContinueLiveOrder.setOrderserialno(orderSerialNo);
        zlContinueLiveOrder.setUserid(userID);
        zlContinueLiveOrder.setHotelid(hotelBasicVO.getHotelID());
        zlContinueLiveOrder.setHotelname(hotelBasicVO.getHotelName());
        zlContinueLiveOrder.setRoomid(hotelBasicVO.getRoomID());
        zlContinueLiveOrder.setRoomnumber(hotelBasicVO.getRoomNumber());
        zlContinueLiveOrder.setUsername(zlWxuserdetail.getRealname());
        zlContinueLiveOrder.setTel(zlWxuserdetail.getTel());
        zlContinueLiveOrder.setOrderstatus((byte) 0);
        zlContinueLiveOrder.setIsdelete(false);
        zlContinueLiveOrder.setIsuserdelete(false);
        zlContinueLiveOrder.setContinuelivedate((int) (zlContinueLiveOrderParam.getContinueLiveDate() / 1000));
        zlContinueLiveOrder.setCheckoutdate((int) (zlContinueLiveOrderParam.getCheckOutDate() / 1000));
        zlContinueLiveOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
        zlContinueLiveOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        zlContinueLiveOrderMapper.insert(zlContinueLiveOrder);
        logger.info("续住订单插入数据库完成,订单id:" + zlContinueLiveOrder.getOrderid());

        // 推送消息
        ZlContinueLiveOrderToPhpVO zlContinueLiveOrderToPhpVO = zlContinueLiveOrderMapper.selectToPhp(zlContinueLiveOrder.getOrderid());
//        PushInfoToPhpUtils.pushInfoToPhp(RedisKeyConstant.TOPIC_CONTINUE_LIVE, zlContinueLiveOrderToPhpVO);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_CONTINUE_LIVE);
        orderPhpSendVO.setMessage(zlContinueLiveOrderToPhpVO);
        Gson gson = new Gson();
        String orderStr = gson.toJson(orderPhpSendVO);
        stringRedisTemplate.convertAndSend(RedisKeyConstant.TOPIC_CONTINUE_LIVE, orderStr);
        logger.info("推送续住订单到redis通知php后台人员完成,订单信息:" + zlContinueLiveOrderToPhpVO);

        Map<String, Object> map = new HashMap<>();
        map.put("orderid", zlContinueLiveOrder.getOrderid());
        return map;
    }

    @Override
    public void cancelCheckoutOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlCheckoutOrderMapper.cancelCheckoutOrder(orderID, updateDate);
    }

    @Override
    public void cancelContinueLiveOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlContinueLiveOrderMapper.cancelContinueLiveOrder(orderID, updateDate);
    }

    @Override
    public void userDeleteCheckoutOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlCheckoutOrderMapper.userDeleteCheckoutOrder(orderID, updateDate);
    }

    @Override
    public void userDeleteContinueLiveOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlContinueLiveOrderMapper.userDeleteContinueLiveOrder(orderID, updateDate);
    }

    /**
     * 退房订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public ZlCheckoutOrder checkoutOrderDetail(Long orderID) {
        return zlCheckoutOrderMapper.checkoutOrderDetail(orderID);
    }

    /**
     * 续住订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public ZlContinueLiveOrder continueLiveOrderDetail(Long orderID) {
        return zlContinueLiveOrderMapper.continueLiveOrderDetail(orderID);
    }


}
