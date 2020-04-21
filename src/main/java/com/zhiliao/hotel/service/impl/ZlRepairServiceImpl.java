package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlRepairMapper;
import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.model.ZlRepair;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ZlRepairServiceImpl implements ZlRepairService {

    @Autowired
    private ZlRepairMapper zlRepairMapper;

    @Autowired
    private ZlRepairorderMapper zlRepairorderMapper;

    @Override
    @Transactional
    public Integer addRepairMsg(ZlRepair repair) throws IOException {
        Integer i = zlRepairMapper.insertzlrepair(repair);
        return i;
    }



    @Override
    @Transactional
    public void addRepairOrderMsg(ZlRepair repair,String hotelname) {
        ZlRepairorder repairorder = new ZlRepairorder();
        repairorder.setUserid(repair.getUserid());   //  用户ID
        repairorder.setSerialnumber(OrderIDUtil.CreateOrderID("BX"));   // 订单ID
        repairorder.setHotelid(repair.getHotelid());        // 酒店ID
        repairorder.setHotelname(hotelname);  //酒店名
        repairorder.setRoomid(repair.getRoomid());  // 房间ID
        repairorder.setRoomnumber(repair.getRoomnumber());  //房间号
        repairorder.setImgurls(repair.getImgurls());    // 图片路径
        repairorder.setAppointmentdate(repair.getAppointmentdate());  // 预约时间
        repairorder.setRemark(repair.getRemark());      // 备注信息
        repairorder.setComeformid(1);           //  来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
        repairorder.setIsdelete(false);     //  是否删除
        repairorder.setOrderstatus((byte)0);    // 订单状态：-1:已取消;0等待确认;1已确认;2已处理
        repairorder.setCreatedate(DateUtils.javaToPhpNowDateTime());    //  下单时间
        repairorder.setUpdatedate(DateUtils.javaToPhpNowDateTime());    //   修改时间
        // 插入图片数据到报修表
        Integer i = zlRepairMapper.insertzlrepairImgUrls(repair.getImgurls(),repair.getUserid(),repair.getCreatedate());
        if(i == 1){
            // 插入数据至报修订单表
            zlRepairorderMapper.insertRepairorder(repairorder);
        }else {
            throw new RuntimeException("图片数据插入失败，请重新再试");
        }

    }


    @Override
    public ZlRepairorder queryRepairOrder(Long Userid) {
        ZlRepairorder zlRepairorder = zlRepairorderMapper.queryByUserIdDescByOrderId(Userid);
        return zlRepairorder;
    }

}
