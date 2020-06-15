package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlRepairServiceImpl implements ZlRepairService {

    @Autowired
    private ZlRepairorderMapper zlRepairorderMapper;


    @Override
    public void addRepairMsg(ZlRepairorder repair) {
//        zlRepairorderMapper.insertRepairorder(repair);
        zlRepairorderMapper.insertSelective(repair);
    }


    @Override
    public Map<String, Object> findRepairOrder(Long orderID) {
        return zlRepairorderMapper.queryRepairMsg(orderID);
    }

    @Override
    public void cancelRepairOrder(Long orderID, Integer updatedate) {
        zlRepairorderMapper.removeRepairOrder(orderID, updatedate);
    }

    /**
     * 用户删除报修订单
     *
     * @param orderID
     */
    @Override
    public void userDeleteRepairOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlRepairorderMapper.userDeleteRepairOrder(orderID, updateDate);
    }
}