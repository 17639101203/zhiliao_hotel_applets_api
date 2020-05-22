package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.common.exception.CommonExceptionEnum;
import com.zhiliao.hotel.controller.serviceorder.params.ServiceorderCommitParams;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderCommitVo;
import com.zhiliao.hotel.controller.serviceorder.vo.ServiceorderInfoVo;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlServiceorderMapper;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlServiceorder;
import com.zhiliao.hotel.service.ZlServiceorderService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZlServiceorderServiceImpl implements ZlServiceorderService {

    private final ZlHotelRoomMapper zlHotelRoomMapper;

    private final ZlServiceorderMapper zlServiceorderMapper;

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
        //校验房间号是否存在
        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(scp.getRoomnumber(), scp.getHotelid());
        if(zlHotelroom == null){
            throw new BizException("该房间号不存在，请再次确认！");
        }
        //todo 校验订单物品数量是否大于当日可提交数量
        //todo 校验送达时间是否在服务时间内
        //根据 token得到微信用户Id
        Long userId = TokenUtil.getUserId(token);
        //生成客房服务订单
        ZlServiceorder order = new ZlServiceorder(
                userId,
                OrderIDUtil.createOrderID("fw"),
                scp.getHotelid(),
                scp.getHotelname(),
                zlHotelroom.getRoomid(),
                zlHotelroom.getRoomnumber(),
                1,
                scp.getRemark(),
                DateUtils.javaToPhpNowDateTime(),
                DateUtils.javaToPhpNowDateTime()
        );
        zlServiceorderMapper.insert(order);
        //todo 插入客服服务订单商品表数据
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
        //todo 获取客房服务订单商品表数据
        //根据字段复制实体
        BeanUtils.copyProperties(order, serviceorderInfoVo);
        //处理时间
        Long timeStamp = DateUtils.phpToJavaDateTime(order.getCreatedate());
        String createdate = DateUtils.transferLongToDate(timeStamp.toString());
        serviceorderInfoVo.setCreatedate(createdate);
        return serviceorderInfoVo;
    }
}
