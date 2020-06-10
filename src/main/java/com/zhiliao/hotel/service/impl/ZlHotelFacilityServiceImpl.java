package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.mapper.ZlHotelFacilityMapper;
import com.zhiliao.hotel.mapper.ZlHotelFacilityOrderMapper;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlHotelFacilityServiceImpl implements ZlHotelFacilityService {

    @Autowired
    private ZlHotelFacilityMapper hotelFacilityMapper;
    @Autowired
    private ZlHotelMapper hotelMapper;

    @Autowired
    private ZlHotelFacilityOrderMapper facilityOrderMapper;

    /**
     * 获取酒店设施列表
     * @param hotelId
     * @return
     */
    @Override
    public List<ZlHotelFacility> getHotelFacilityList(Integer hotelId) {
        return hotelFacilityMapper.getHotelFacilityList(hotelId);
    }

    /**
     * 酒店设施预定详情
     * @param facilityId
     * @return
     */
    @Override
    public ZlHotelFacility getHotelFacilityDetail(Integer facilityId) {
        return hotelFacilityMapper.getHotelFacilityDetail(facilityId);
    }

    /**
     * 酒店设施预定
     * @param zlHotelFacilityOrder
     * @return
     */
    @Override
    public Map<String,Object> addFacilityOrder(ZlHotelFacilityOrder zlHotelFacilityOrder) {
        Integer facilityID = zlHotelFacilityOrder.getFacilityid();
        //定义map集合,用于封装返回信息
        Map<String,Object> map = new HashMap<>();

        ZlHotelFacility hotelFacilityDetail = hotelFacilityMapper.getHotelFacilityDetail(facilityID);
        if (hotelFacilityDetail == null) {
            throw  new RuntimeException("该酒店没有此设施,请询问酒店前台");
        }

        //开始时间
        Integer beginusedate = zlHotelFacilityOrder.getUsebegindate();
        //结束时间
        Integer endusedate = zlHotelFacilityOrder.getUseenddate();
        //付费项目
        if (hotelFacilityDetail.getPrice().compareTo(new BigDecimal(0)) == 1) {
            //判断酒店设施数量是否充足
            if (hotelFacilityDetail.getFacilitycount() <= 0) {
                throw  new RuntimeException("酒店设施数量不足!");
            }
            //判断该时间段是否在营业时间
            if (hotelFacilityDetail.getServicebegindate() > beginusedate && hotelFacilityDetail.getServiceenddate() < endusedate){
                throw  new RuntimeException("该时间段不在此设施的服务时间,请重新选择!");
            }
        }
        //生成订单编号
        zlHotelFacilityOrder.setSerialnumber(OrderIDUtil.createOrderID("HS"));
        zlHotelFacilityOrder.setComeformid(1);
        ZlHotel zlHotel = hotelMapper.getById(zlHotelFacilityOrder.getHotelid());
        if (zlHotel != null){
            //获取酒店名称
            zlHotelFacilityOrder.setHotelname(zlHotel.getHotelName());
        }
        zlHotelFacilityOrder.setOrderstatus((byte) 0);
        zlHotelFacilityOrder.setCreatedate(Math.toIntExact(System.currentTimeMillis() / 1000));

        int insert = facilityOrderMapper.insertSelective(zlHotelFacilityOrder);
        //是否下单成功
        if (hotelFacilityDetail.getPrice().compareTo(new BigDecimal(0)) == 1) {
            if (insert > 0) {
                hotelFacilityMapper.updateCount(facilityID, Math.toIntExact(System.currentTimeMillis() / 1000));
            }else {
                throw new RuntimeException("提交失败");
            }
        }
        map.put("orderId",zlHotelFacilityOrder.getOrderid());
        return map;
    }
}
