package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlRepair;
import com.zhiliao.hotel.model.ZlRepairorder;

import java.io.IOException;

public interface ZlRepairService {

    public void addRepairMsg(ZlRepair repair, String hotelname) throws IOException;


    public ZlRepairorder queryRepairOrder(Long Userid);
}
