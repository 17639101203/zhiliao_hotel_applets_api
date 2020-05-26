package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlRepairorder;

import java.io.IOException;
import java.util.Map;

public interface ZlRepairService {

    /**
     * 新增报修信息
     * @param zlRepairorder   报修信息
     * @return
     * @throws IOException
     */
    void addRepairMsg(ZlRepairorder zlRepairorder);


    /**
     * 查询报修订单详情
     * @param
     * @return
     */
    Map<String,Object> findRepairOrder(Long Userid,String serialnumber);


    /**
     * 取消报修预约
     */
    void cancelRepairOrder(Long Userid,String serialnumber,Integer updatedate);
}
