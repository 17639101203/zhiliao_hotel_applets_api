package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.controller.myAppointment.dto.HotelFacilityOrderParamDTO;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlCleanOrderDTO;
import com.zhiliao.hotel.controller.myAppointment.dto.ZlHotelFacilityOrderDTO;
import com.zhiliao.hotel.mapper.ZlHotelFacilityMapper;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.OrderLogService;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityOrderServiceImpl implements ZlHotelFacilityOrderService {

    @Autowired
    private ZlHotelFacilityOrderMapper hotelFacilityOrderMapper;

    @Autowired
    private ZlHotelFacilityMapper facilityMapper;

    @Autowired
    private OrderLogService orderLogService;

    /**
     * 酒店设施订单列表
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllOrder(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlHotelFacilityOrder> hotelFacilityOrderList = hotelFacilityOrderMapper.findAllOrder(userId, orderstatus, hotelId);
        PageInfo<ZlHotelFacilityOrder> zlHotelFacilityOrderPageInfo = new PageInfo<ZlHotelFacilityOrder>(hotelFacilityOrderList);

        for (ZlHotelFacilityOrder zlHotelFacilityOrder : hotelFacilityOrderList) {
            if (zlHotelFacilityOrder.getPaystatus() == 2 && zlHotelFacilityOrder.getOrderstatus() == 1) {
                zlHotelFacilityOrder.setOrderstatus((byte) 2);
            }
        }

        List<ZlHotelFacilityOrderDTO> zlHotelFacilityOrderDTOS = new LinkedList<>();
        for (ZlHotelFacilityOrder zlHotelFacilityOrder : hotelFacilityOrderList) {
            ZlHotelFacilityOrderDTO zlHotelFacilityOrderDTO = new ZlHotelFacilityOrderDTO();
            if (zlHotelFacilityOrder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlHotelFacilityOrder, zlHotelFacilityOrderDTO);
                zlHotelFacilityOrderDTO.setStatusmessage("已取消");
            }
            if (zlHotelFacilityOrder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlHotelFacilityOrder, zlHotelFacilityOrderDTO);
                zlHotelFacilityOrderDTO.setStatusmessage("等待确认");
            }
            if (zlHotelFacilityOrder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlHotelFacilityOrder, zlHotelFacilityOrderDTO);
                zlHotelFacilityOrderDTO.setStatusmessage("已确认");
            }
            if (zlHotelFacilityOrder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlHotelFacilityOrder, zlHotelFacilityOrderDTO);
                zlHotelFacilityOrderDTO.setStatusmessage("已接单");
            }
            zlHotelFacilityOrderDTOS.add(zlHotelFacilityOrderDTO);
        }

        PageInfo<ZlHotelFacilityOrderDTO> pageInfo = new PageInfo<>(zlHotelFacilityOrderDTOS);
        pageInfo.setTotal(zlHotelFacilityOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlHotelFacilityOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlHotelFacilityOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlHotelFacilityOrderPageInfo.getTotal() % zlHotelFacilityOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlHotelFacilityOrderPageInfo.getTotal() / zlHotelFacilityOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 酒店设施订单详情
     *
     * @param orderID
     * @return
     */
    @Override
    public HotelFacilityOrderParamDTO findOrder(Long orderID) {
        HotelFacilityOrderParamDTO hotelFacilityOrderParamDTO = hotelFacilityOrderMapper.findOrderByIdVO(orderID);
        if (hotelFacilityOrderParamDTO != null) {
            Integer cancancelorderminute = hotelFacilityOrderParamDTO.getCancancelorderminute();
            hotelFacilityOrderParamDTO.setCancancelorderminute(hotelFacilityOrderParamDTO.getUsebegindate() - cancancelorderminute * 60);
            if (hotelFacilityOrderParamDTO.getPaystatus() == 2 && hotelFacilityOrderParamDTO.getOrderstatus() == 1) {
                hotelFacilityOrderParamDTO.setOrderstatus((byte) 2);
            }
        }
        return hotelFacilityOrderParamDTO;
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

        if (facilityOrder == null) {
            throw new BizException("参数有误!");
        }
        if (date - facilityOrder.getCreatedate() > oneHour) {
            throw new RuntimeException("很抱歉，可取消订单时间已过，现在已不能取消！");
        }

        //将订单取消操作写到记录表中
        orderLogService.cancelOrderLog(facilityOrder.getOrderid(), facilityOrder.getHotelid(), facilityOrder.getCreatedate(), facilityOrder.getMoldtype());

        hotelFacilityOrderMapper.updateById(orderID, updateDate);
        //释放该设施数量
        facilityMapper.updateByFacilityId(facilityOrder.getFacilityid(), updateDate);

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
