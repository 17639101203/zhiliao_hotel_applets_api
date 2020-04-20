package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.mapper.ZlCleanOrderMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCleanOrderServiceImpl implements ZlCleanOrderService {

    @Autowired
    private ZlCleanOrderMapper zlCleanOrderMapper;

    @Override
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder) {
        return zlCleanOrderMapper.addCleanOrder(zlCleanOrder);
    }

    /**
     * 获取所有清扫订单
     * @param userId  用户id
     * @param orderstatus  服务状态
     * @param pageNum  分页起始页
     * @param pageSize  每页的条数
     * @return
     */
    @Override
    public List<ZlCleanOrder> findAllByStatus(Long userId, Integer orderstatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ZlCleanOrder> cleanorders = zlCleanOrderMapper.findAllByStatus(userId,orderstatus);
        if (cleanorders != null){
            for (int i = 0; i < cleanorders.size(); i++) {
                ZlCleanOrder zlCleanorder = cleanorders.get(i);
                zlCleanorder.setFuwutype("清扫服务");
            }
        }
        PageInfo pageInfo = new PageInfo(cleanorders);
        return pageInfo.getList();
    }
    /**
     * 清扫订单详情
     * @param orderID
     * @return
     */
    @Override
    public ZlCleanOrder orderDetail(Long orderID) {
        ZlCleanOrder cleanorder = zlCleanOrderMapper.OrderDetail(orderID);
        cleanorder.setFuwutype("清扫");
        return cleanorder;
    }
}
