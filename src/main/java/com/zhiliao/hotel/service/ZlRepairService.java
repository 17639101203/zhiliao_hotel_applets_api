package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.zlRepair;
import com.zhiliao.hotel.model.zlRepairorder;

import java.io.IOException;
import java.util.List;

public interface ZlRepairService {

    public void addRepairMsg(zlRepair repair,String hotelname) throws IOException;

    List<zlRepairorder> findAllByUserId(Long userId, Integer orderstatus, Integer pageNum, Integer pageSize);

    zlRepairorder orderDetail(Long orderID);
}
