package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Transactional(rollbackFor=Exception.class)
@Service
public class ZlRepairServiceImpl implements ZlRepairService {

    @Autowired
    private  ZlRepairorderMapper zlRepairorderMapper;



    @Override
    public void addRepairMsg(ZlRepairorder repair) {
        zlRepairorderMapper.insertRepairorder(repair);
    }


    @Override
    public Map<String,Object> findRepairOrder(Long Userid,String serialnumber) {
        return zlRepairorderMapper.queryRepairMsg(Userid,serialnumber);
    }

    @Override
    public void cancelRepairOrder(Long Userid, String serialnumber, Integer updatedate) {
        zlRepairorderMapper.removeRepairOrder(Userid,serialnumber,updatedate);
    }
}