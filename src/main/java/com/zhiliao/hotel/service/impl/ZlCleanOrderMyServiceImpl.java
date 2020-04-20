package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlCleanOrderMapper;
import com.zhiliao.hotel.mapper.ZlCleanOrderMyMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.service.ZlCleanOrderMyService;
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
public class ZlCleanOrderMyServiceImpl implements ZlCleanOrderMyService {

    @Autowired
    private ZlCleanOrderMyMapper zlCleanOrderMyMapper;



    /**
     * 获取所有清扫订单
     * @param userId  用户id
     * @param orderstatus  服务状态
     * @param pageNo  分页起始页
     * @param pageSize  每页的条数
     * @return
     */
    @Override
    public PageInfoResult findAllByStatus(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlCleanOrder> cleanorders = zlCleanOrderMyMapper.findAllByStatus(userId,orderstatus);
        if (cleanorders != null){
            for (int i = 0; i < cleanorders.size(); i++) {
                ZlCleanOrder zlCleanorder = cleanorders.get(i);
                zlCleanorder.setFuwutype("清扫服务");
            }
        }
        PageInfo<ZlCleanOrder> pageInfo = new PageInfo<>(cleanorders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }
    /**
     * 清扫订单详情
     * @param orderID
     * @return
     */
    @Override
    public ZlCleanOrder orderDetail(Long orderID) {
        ZlCleanOrder cleanorder = zlCleanOrderMyMapper.OrderDetail(orderID);
        cleanorder.setFuwutype("清扫");
        return cleanorder;
    }
}
