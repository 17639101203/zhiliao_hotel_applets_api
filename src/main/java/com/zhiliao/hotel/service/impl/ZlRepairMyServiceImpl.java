package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlRepairMapper;
import com.zhiliao.hotel.mapper.ZlRepairorderMyMapper;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZlRepairMyServiceImpl implements ZlRepairMyService {

    @Autowired
    private ZlRepairMapper zlRepairMapper;

    @Autowired
    private ZlRepairorderMyMapper zlRepairorderMapper;


    /**
     * 报修服务订单查询
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllByUserId(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlRepairorder> repairOrders = zlRepairorderMapper.findAllByUserId(userId,orderstatus);
        for (int i = 0; i < repairOrders.size(); i++) {
            ZlRepairorder zlRepairOrder = repairOrders.get(i);
            zlRepairOrder.setFuwutype("报修服务");
        }
        PageInfo<ZlRepairorder> pageInfo = new PageInfo<>(repairOrders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public ZlRepairorder orderDetail(Long orderID) {
        ZlRepairorder repairOrder = zlRepairorderMapper.findDetail(orderID);
        repairOrder.setFuwutype("报修");
        return repairOrder;
    }
}
