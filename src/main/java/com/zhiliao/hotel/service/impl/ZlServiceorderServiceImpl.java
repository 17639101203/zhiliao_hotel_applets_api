package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpSendVO;
import com.zhiliao.hotel.controller.myOrder.vo.OrderPhpVO;
import com.zhiliao.hotel.controller.serviceorder.params.ServiceorderCommitParams;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderCommitVo;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderInfoVo;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlServiceorderService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ZlServiceorderServiceImpl implements ZlServiceorderService {

    @Autowired
    private final ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private final ZlServiceorderMapper zlServiceorderMapper;

    @Autowired
    private final ZlServicegoodsMapper zlServicegoodsMapper;

    @Autowired
    private final ZlServiceorderdetailMapper zlServiceorderdetailMapper;

    @Autowired
    private final ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Autowired
    private ZlHotelMapper zlHotelMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ServiceorderCommitVo serviceorderSubmit(String token, ServiceorderCommitParams scp) throws RuntimeException {
        ServiceorderCommitVo serviceorderCommitVo = new ServiceorderCommitVo();
        //校验参数
        if (scp.getHotelid() == null) {
            throw new BizException("酒店id不能为空");
        }
        if (StringUtils.isEmpty(scp.getHotelname())) {
            throw new BizException("酒店名称不能为空");
        }
        if (StringUtils.isEmpty(scp.getRoomnumber())) {
            throw new BizException("房间号不能为空");
        }
        if (scp.getDeliverydate() == null) {
            throw new BizException("送达时间不能为空");
        }
        if (CollectionUtils.isEmpty(scp.getOrderGoods())) {
            throw new BizException("未选择商品，请确认！");
        }
        //校验房间号是否存在
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(scp.getRoomnumber(), scp.getHotelid());
        if (zlHotelroom == null) {
            throw new BizException("该房间号不存在，请再次确认！");
        }
        //获取订单商品id集合
        String goodIds = scp.getOrderGoods().stream().map(goods -> goods.getGoodsId().toString()).collect(Collectors.joining(","));
        List<ZlServicegoods> zlServicegoodsList = zlServicegoodsMapper.findAllByHotelIdAndGoodsIds(scp.getHotelid(), goodIds);
        if (CollectionUtils.isEmpty(zlServicegoodsList)) {
            throw new BizException("选择的商品有误，请刷新后重试！");
        }
        //获取PHP当日时间戳
        Integer start = DateUtils.todayFirstDate();
        Integer end = DateUtils.todayLastDate();
        //获取订单商品当日已购买次数
        List<ZlServiceorderdetail> zlServiceorderdetails = zlServiceorderdetailMapper.getBuyNumByOrderIds(goodIds, scp.getHotelid(), zlHotelroom.getRoomid(), start, end);
        Map<Integer, Integer> orderHasBuyNum = zlServiceorderdetails.stream().collect(Collectors.toMap(ZlServiceorderdetail::getGoodsid, ZlServiceorderdetail::getBuyNum));
        //获取订单商品当日已购买数量
        Map<Integer, Integer> orderGoodsHasBuyNum = zlServiceorderdetails.stream().collect(Collectors.toMap(ZlServiceorderdetail::getGoodsid, ZlServiceorderdetail::getGoodscount));
        //订单商品购买次数
        Map<Integer, Integer> orderBuyNum = scp.getOrderGoods().stream().collect(Collectors.toMap(ServiceorderCommitParams.orderGoods::getGoodsId, ServiceorderCommitParams.orderGoods::getGoodsCount));
        //第一张商品图片地址
        String goodscoverurl = "";
        boolean isFirst = true;
        List<ZlServiceorderdetail> orderDetails = new ArrayList<>();
        //根据 token得到微信用户Id
        Long userId = TokenUtil.getUserId(token);
        //校验订单物品数量是否大于当日可提交数量
        for (ZlServicegoods zlServicegoods : zlServicegoodsList) {
            //判断订单商品是否超过该商品单次购买数量
            Integer buyNum = orderBuyNum.get(zlServicegoods.getGoodsid());
            if (buyNum.compareTo(0) <= 0) {
                throw new BizException(String.format("商品%s未选择购买数量，请重新选择！", zlServicegoods.getGoodsname()));
            }
            if (orderGoodsHasBuyNum.containsKey(zlServicegoods.getGoodsid())) {
                if ((buyNum + orderGoodsHasBuyNum.get(zlServicegoods.getGoodsid())) > zlServicegoods.getDaymaxgoodscount()) {
                    throw new BizException(String.format("商品%s超过每日可购买数量%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getDaymaxgoodscount()));
                }
            } else {
                if (buyNum.compareTo(zlServicegoods.getDaymaxgoodscount()) > 0) {
                    throw new BizException(String.format("商品%s超过每日可购买数量%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getDaymaxgoodscount()));
                }
            }
            if (zlServicegoods.getDaymaxcount().compareTo(0) == 0) {
                throw new BizException(String.format("商品%s超过每日可购买次数%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getDaymaxcount()));
            }
            if (orderHasBuyNum.containsKey(zlServicegoods.getGoodsid())) {
                if (orderHasBuyNum.get(zlServicegoods.getGoodsid()).compareTo(zlServicegoods.getDaymaxcount()) >= 0) {
                    throw new BizException(String.format("商品%s超过每日可购买次数%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getDaymaxcount()));
                }
            }
            if (isFirst) {
                goodscoverurl = zlServicegoods.getCoverimgurl();
                isFirst = false;
            }
            ZlServiceorderdetail orderDetail = new ZlServiceorderdetail();
            orderDetail.setUserid(userId);
            orderDetail.setGoodsid(zlServicegoods.getGoodsid());
            orderDetail.setGoodsname(zlServicegoods.getGoodsname());
            orderDetail.setGoodscoverurl(zlServicegoods.getCoverimgurl());
            orderDetail.setPrice(zlServicegoods.getSaleprice());
            orderDetail.setGoodscount(buyNum);
            orderDetail.setCreatedate(DateUtils.javaToPhpNowDateTime());
            orderDetail.setUpdatedate(DateUtils.javaToPhpNowDateTime());
            orderDetails.add(orderDetail);
        }
        //todo 校验送达时间是否在服务时间内
        //todo 超时时间  暂时按15分钟  送达时间、超时时间要放到redis，key暂定
        int timeoutDate;
        if (scp.getDeliverydate() == 0) {
            timeoutDate = DateUtils.javaToPhpNowDateTime() + (15 * 60);
        } else {
            int date = (int) (scp.getDeliverydate() / 1000);
            timeoutDate = date + (15 * 60);
        }
        //获取用户信息
        ZlWxuserdetail zlWxuserdetail = Optional.ofNullable(zlWxuserdetailMapper.findByUserId(userId)).orElse(new ZlWxuserdetail());
        //生成订单编号
        String orderSerialNo = OrderIDUtil.createOrderID("");
        //生成客房服务订单
        ZlServiceorder order = new ZlServiceorder().builder()
                .userid(userId)
                .username(zlWxuserdetail.getRealname() == null ? "" : zlWxuserdetail.getRealname())
                .tel(zlWxuserdetail.getTel() == null ? "" : zlWxuserdetail.getTel())
                .serialnumber(orderSerialNo)
                .hotelid(scp.getHotelid())
                .hotelname(scp.getHotelname())
                .roomid(zlHotelroom.getRoomid())
                .goodscoverurl(goodscoverurl)
                .floornumber(zlHotelroom.getRoomfloor())
                .roomnumber(zlHotelroom.getRoomnumber())
                .comeformid(1)
                .deliverydate((int) (scp.getDeliverydate() / 1000))
                .timeoutdate(timeoutDate)
                .remark(scp.getRemark())
                .createdate(DateUtils.javaToPhpNowDateTime())
                .updatedate(DateUtils.javaToPhpNowDateTime())
                .build();
        zlServiceorderMapper.insert(order);
        //插入客服服务订单商品表数据
        orderDetails.stream().forEach(orderDetail -> orderDetail.setOrderid(order.getOrderid()));
        zlServiceorderdetailMapper.insertOrderDetailList(orderDetails);
        //返回客房服务订单id
        serviceorderCommitVo.setOrderid(order.getOrderid());
        //todo 获取商家服务平均时间配置，目前默认15分钟
        serviceorderCommitVo.setDealWithTime(15);

        // 推送消息
//        Map<String, Object> roomServiceMap = new HashMap<>();
//        OrderPhpVO orderPhpVO = new OrderPhpVO();
//        roomServiceMap.put("form", "java");
//        roomServiceMap.put("channel", RedisKeyConstant.TOPIC_ROOMSERVICE);
//        orderPhpVO.setOrderSerialNo(orderSerialNo);
//        orderPhpVO.setGetHotelId(scp.getHotelid());
//        roomServiceMap.put("message", orderPhpVO);
//        redisTemplate.convertAndSend(RedisKeyConstant.TOPIC_ROOMSERVICE, roomServiceMap);
        OrderPhpSendVO orderPhpSendVO = new OrderPhpSendVO();
        OrderPhpVO orderPhpVO = new OrderPhpVO();
        orderPhpVO.setOrderSerialNo(orderSerialNo);
        orderPhpVO.setGetHotelId(scp.getHotelid());
        orderPhpSendVO.setForm("java");
        orderPhpSendVO.setChannel(RedisKeyConstant.TOPIC_ROOMSERVICE);
        orderPhpSendVO.setMessage(orderPhpVO);
        redisTemplate.convertAndSend(RedisKeyConstant.TOPIC_ROOMSERVICE, orderPhpSendVO);


        return serviceorderCommitVo;
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceorderInfoVo getServiceorderInfo(Long orderId) throws RuntimeException {
        ServiceorderInfoVo serviceorderInfoVo = new ServiceorderInfoVo();
        //获取客房服务订单详情
        ZlServiceorder order = zlServiceorderMapper.getByOrderId(orderId);
        if (order == null) {
            throw new BizException("该订单不存在，请刷新页面重试！");
        }
        //获取客房服务订单详情表数据
        List<ZlServiceorderdetail> zlServiceorderdetails = zlServiceorderdetailMapper.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(zlServiceorderdetails)) {
            throw new BizException("该订单商品详情不存在，请刷新页面重试！");
        }
        List<ServiceorderInfoVo.goods> orderGoodsList = new ArrayList<>();
        for (ZlServiceorderdetail zlServiceorderdetail : zlServiceorderdetails) {
            ServiceorderInfoVo.goods orderGoods = new ServiceorderInfoVo.goods();
            BeanUtils.copyProperties(zlServiceorderdetail, orderGoods);
            orderGoodsList.add(orderGoods);
        }
        serviceorderInfoVo.setOrderGoodsList(orderGoodsList);
        //根据字段复制实体
        BeanUtils.copyProperties(order, serviceorderInfoVo);
//        String createdate = DateUtils.transferLongToDate(DateUtils.phpToJavaDateTime(order.getCreatedate()).toString());
//        serviceorderInfoVo.setCreatedate(createdate);
//        if (order.getDeliverydate() == 0) {
//            serviceorderInfoVo.setDeliverydate("0");
//        } else {
//            String bookdate = DateUtils.transferLongToDate(DateUtils.phpToJavaDateTime(order.getDeliverydate()).toString());
//            serviceorderInfoVo.setDeliverydate(bookdate);
//        }
        ZlHotel zlHotel = zlHotelMapper.getById(serviceorderInfoVo.getHotelid());
        serviceorderInfoVo.setHotelname(zlHotel.getHotelName());
        return serviceorderInfoVo;
    }

    @Override
    public void serviceorderCancel(Long orderId) throws RuntimeException {
        //获取客房服务订单详情
        ZlServiceorder order = zlServiceorderMapper.getByOrderId(orderId);
        if (order == null) {
            throw new BizException("该订单不存在，请刷新页面重试！");
        }
        if (order.getOrderstatus() != 0) {
            throw new BizException("该订单状态已发生改变，请刷新页面重试！");
        }
        //修改订单状态
        Integer updateDate = DateUtils.javaToPhpNowDateTime();
        zlServiceorderMapper.updateOrderStatusById(orderId, updateDate);
    }

    /**
     * 用户删除客房服务订单
     *
     * @param orderid
     */
    @Override
    public void userDeleteServiceOrder(Long orderid) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlServiceorderMapper.userDeleteServiceOrder(orderid, updateDate);
    }

}
