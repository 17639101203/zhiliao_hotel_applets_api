package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlRepairorder;

import java.io.IOException;

public interface ZlRepairService {

//    /**
//     * 新增报修信息
//     * @param repair   报修信息
//     * @return
//     * @throws IOException
//     */
//    Integer addRepairMsg(ZlRepair repair) throws IOException;


    /**
     * 查询报修订单信息
     * @param Userid
     * @return
     */
    ZlRepairorder queryRepairOrder(Long Userid);

//
//    /**
//     * 新增报修订单信息，报修图片地址添加
//     * @param repair    报修信息
//     * @param hotelname 酒店名
//     */
//    void addRepairOrderMsg(ZlRepair repair,String hotelname);
}
