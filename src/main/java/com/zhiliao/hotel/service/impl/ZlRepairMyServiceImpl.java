package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlRepairMapper;
import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.model.zlRepair;
import com.zhiliao.hotel.model.zlRepairorder;
import com.zhiliao.hotel.service.ZlRepairMyService;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class ZlRepairMyServiceImpl implements ZlRepairMyService {

    @Autowired
    private ZlRepairMapper zlRepairMapper;

    @Autowired
    private ZlRepairorderMapper zlRepairorderMapper;


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
        List<zlRepairorder> repairOrders = zlRepairorderMapper.findAllByUserId(userId,orderstatus);
        for (int i = 0; i < repairOrders.size(); i++) {
            zlRepairorder zlRepairOrder = repairOrders.get(i);
            zlRepairOrder.setFuwutype("报修服务");
        }
        PageInfo<zlRepairorder> pageInfo = new PageInfo<>(repairOrders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public zlRepairorder orderDetail(Long orderID) {
        zlRepairorder repairOrder = zlRepairorderMapper.findDetail(orderID);
        repairOrder.setFuwutype("报修");
        return repairOrder;
    }
}
