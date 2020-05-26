package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZlServiceorderServiceImpl implements ZlServiceorderService {

    private final ZlHotelRoomMapper zlHotelRoomMapper;

    private final ZlServiceorderMapper zlServiceorderMapper;

    private final ZlServicegoodsMapper zlServicegoodsMapper;

    private final ZlServiceorderdetailMapper zlServiceorderdetailMapper;

    private final ZlWxuserdetailMapper zlWxuserdetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceorderCommitVo serviceorderSubmit(String token, ServiceorderCommitParams scp) throws RuntimeException{
        ServiceorderCommitVo serviceorderCommitVo = new ServiceorderCommitVo();
        //校验参数
        if(scp.getHotelid() == null){
            throw new BizException("酒店id不能为空");
        }
        if(StringUtils.isEmpty(scp.getHotelname())){
            throw new BizException("酒店名称不能为空");
        }
        if(StringUtils.isEmpty(scp.getRoomnumber())){
            throw new BizException("房间号不能为空");
        }
        if(scp.getBookdate() == null){
            throw new BizException("预约时间不能为空");
        }
        if(CollectionUtils.isEmpty(scp.getOrderGoods())){
            throw new BizException("未选择商品，请确认！");
        }
        //校验房间号是否存在
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(scp.getRoomnumber(), scp.getHotelid());
        if(zlHotelroom == null){
            throw new BizException("该房间号不存在，请再次确认！");
        }
        //获取订单商品id集合
        String goodIds = scp.getOrderGoods().stream().map(goods -> goods.getGoodsId().toString()).collect(Collectors.joining(","));
        List<ZlServicegoods> zlServicegoodsList = zlServicegoodsMapper.findAllByHotelIdAndGoodsIds(scp.getHotelid(), goodIds);
        if(CollectionUtils.isEmpty(zlServicegoodsList)){
            throw new BizException("选择的商品有误，请刷新后重试！");
        }
        //获取PHP当日时间戳
        Integer start = DateUtils.todayFirstDate();
        Integer end = DateUtils.todayLastDate();
        //获取订单商品当日已购买次数
        List<ZlServiceorderdetail> zlServiceorderdetails = zlServiceorderdetailMapper.getBuyNumByOrderIds(goodIds, scp.getHotelid(), zlHotelroom.getRoomid(), start, end);
        Map<Integer, Integer> orderHasBuyNum = zlServiceorderdetails.stream().collect(Collectors.toMap(ZlServiceorderdetail::getGoodsid, ZlServiceorderdetail::getBuyNum));
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
            if(buyNum.compareTo(0) <= 0){
                throw new BizException(String.format("商品%s未选择购买数量，请重新选择！", zlServicegoods.getGoodsname()));
            }
            if(buyNum.compareTo(zlServicegoods.getApplylimitcount()) > 0){
                throw new BizException(String.format("商品%s超过单次可购买数量%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getApplylimitcount()));
            }
            if(zlServicegoods.getApplymaxcount().compareTo(0) == 0){
                throw new BizException(String.format("商品%s超过每日可购买次数%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getApplymaxcount()));
            }
            if(orderHasBuyNum.containsKey(zlServicegoods.getGoodsid())){
                if(orderHasBuyNum.get(zlServicegoods.getGoodsid()).compareTo(zlServicegoods.getApplymaxcount()) >= 0){
                    throw new BizException(String.format("商品%s超过每日可购买次数%s，请重新选择！", zlServicegoods.getGoodsname(), zlServicegoods.getApplymaxcount()));
                }
            }
            if(isFirst){
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
            orderDetails.add(orderDetail);
        }
        //todo 校验送达时间是否在服务时间内
        //todo 超时时间  暂时按15分钟  送达时间、超时时间要放到redis，key暂定
        Integer timeoutDate = scp.getBookdate() + (15 * 60);
        //获取用户信息
        ZlWxuserdetail zlWxuserdetail = Optional.ofNullable(zlWxuserdetailMapper.findByUserId(userId)).orElse(new ZlWxuserdetail());
        //生成客房服务订单
        ZlServiceorder order = new ZlServiceorder(
                userId,
                zlWxuserdetail.getRealname() == null ? "" : zlWxuserdetail.getRealname(),
                zlWxuserdetail.getTel() == null ? "" : zlWxuserdetail.getTel(),
                OrderIDUtil.createOrderID("fw"),
                scp.getHotelid(),
                scp.getHotelname(),
                zlHotelroom.getRoomid(),
                goodscoverurl,
                zlHotelroom.getRoomfloor(),
                zlHotelroom.getRoomnumber(),
                1, //来源，1表示小程序C端
                scp.getBookdate(),
                timeoutDate,
                scp.getRemark(),
                DateUtils.javaToPhpNowDateTime(),
                DateUtils.javaToPhpNowDateTime()
        );
        zlServiceorderMapper.insert(order);
        //插入客服服务订单商品表数据
        orderDetails.stream().forEach(orderDetail -> orderDetail.setOrderid(order.getOrderid()));
        zlServiceorderdetailMapper.insertOrderDetailList(orderDetails);
        //返回客房服务订单id
        serviceorderCommitVo.setOrderid(order.getOrderid());
        //todo 获取商家服务平均时间配置，目前默认15分钟
        serviceorderCommitVo.setDealWithTime(15);
        return serviceorderCommitVo;
    }

    @Override
    public ServiceorderInfoVo getServiceorderInfo(Long orderId) throws RuntimeException{
        ServiceorderInfoVo serviceorderInfoVo = new ServiceorderInfoVo();
        //获取客房服务订单详情
        ZlServiceorder order = zlServiceorderMapper.getByOrderId(orderId);
        if(order == null){
            throw new BizException("该订单不存在，请刷新页面重试！");
        }
        //获取客房服务订单详情表数据
        List<ZlServiceorderdetail> zlServiceorderdetails = zlServiceorderdetailMapper.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(zlServiceorderdetails)){
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
        //处理时间
        String createdate = DateUtils.transferLongToDate(DateUtils.phpToJavaDateTime(order.getCreatedate()).toString());
        serviceorderInfoVo.setCreatedate(createdate);
        String bookdate = DateUtils.transferLongToDate(DateUtils.phpToJavaDateTime(order.getBookdate()).toString());
        serviceorderInfoVo.setBookdate(bookdate);
        return serviceorderInfoVo;
    }

}
