package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.mapper.ZlHotelFacilityMapper;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityOrderServiceImpl implements ZlHotelFacilityOrderService {

    @Autowired
    private ZlHotelFacilityOrderMapper hotelFacilityOrderMapper;

    @Autowired
    private ZlHotelFacilityMapper facilityMapper;

    /**
     * 酒店设施订单列表
     *
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlHotelFacilityOrder> hotelFacilityOrderList = hotelFacilityOrderMapper.findAllOrder(userId, orderStatus);

        PageInfo<ZlHotelFacilityOrder> pageInfo = new PageInfo<>(hotelFacilityOrderList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 酒店设施订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public HotelFacilityOrderParamVO findOrder(Long orderID) {
        HotelFacilityOrderParamVO hotelFacilityOrderParamVO = hotelFacilityOrderMapper.findOrderByIdVO(orderID);
        if (hotelFacilityOrderParamVO != null) {
            Integer cancancelorderminute = hotelFacilityOrderParamVO.getCancancelorderminute();
            hotelFacilityOrderParamVO.setCancancelorderminute(hotelFacilityOrderParamVO.getUsebegindate() + cancancelorderminute * 60);
        }
        return hotelFacilityOrderParamVO;
    }

    /**
     * 取消酒店设施订单
     *
     * @param orderID
     */
    @Override
    public void cancelFacilityOrder(Long orderID) {

        //当前时间
        Integer date = Math.toIntExact(System.currentTimeMillis() / 1000);
        ZlHotelFacilityOrder facilityOrder = hotelFacilityOrderMapper.findOrderById(orderID);
        ZlHotelFacility hotelFacilityDetail = facilityMapper.getHotelFacilityDetail(facilityOrder.getFacilityid());
        //可取消订单时间
        Integer oneHour = hotelFacilityDetail.getCancancelorderminute() * 60;
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);

        if (facilityOrder != null) {
            if (date - facilityOrder.getCreatedate() > oneHour) {
                throw new RuntimeException("很抱歉，可取消订单时间已过，现在已不能取消！");
            }
            hotelFacilityOrderMapper.updateById(orderID, updateDate);
            //释放该设施数量
            facilityMapper.updateByFacilityId(facilityOrder.getFacilityid(), updateDate);
        }

    }

    /**
     * 用户删除设施订单
     *
     * @param orderID
     */
    @Override
    public void userDeleteFacilityOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        hotelFacilityOrderMapper.userDeleteFacilityOrder(orderID, updateDate);
    }
}
