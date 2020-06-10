package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
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
    public ZlHotelFacilityOrder findOrder(Long orderID) {
        return hotelFacilityOrderMapper.findOrderById(orderID);
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

        if (facilityOrder != null) {
            if (facilityOrder.getUsebegindate() - date <= oneHour) {
                throw new RuntimeException("很抱歉，需提前" + hotelFacilityDetail.getCancancelorderminute() + "小时取消预约，现在已不能取消！");
            }
            facilityOrder.setOrderstatus((byte) -1);
            facilityOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            hotelFacilityOrderMapper.updateById(facilityOrder);

            //释放该设施数量
            ZlHotelFacility facilityDetail = facilityMapper.getHotelFacilityDetail(facilityOrder.getFacilityid());
            if (facilityDetail != null) {
                facilityDetail.setFacilitycount(facilityDetail.getFacilitycount() + 1);
                facilityDetail.setFacilityremaincount(facilityDetail.getFacilityremaincount() + 1);
                facilityDetail.setUsecount(facilityDetail.getUsecount() - 1);
                facilityDetail.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
                int num = facilityMapper.updateByFacilityId(facilityDetail);
                if (num == 0){
                    throw new RuntimeException("取消失败");
                }
            }
        }

    }

    /**
     * 用户删除设施订单
     * @param orderID
     */
    @Override
    public void userDeleteFacilityOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        hotelFacilityOrderMapper.userDeleteFacilityOrder(orderID,updateDate);
    }
}
