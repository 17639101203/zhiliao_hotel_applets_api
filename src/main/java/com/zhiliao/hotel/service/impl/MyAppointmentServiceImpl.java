package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlCleanOrderMapper;
import com.zhiliao.hotel.mapper.ZlCleanOrderMyMapper;
import com.zhiliao.hotel.mapper.ZlInvoiceMyMapper;
import com.zhiliao.hotel.mapper.ZlRepairorderMyMapper;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.MyAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 我的预约服务订单
 * @Author:
 * @Date: 2020/4/14 13:52
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MyAppointmentServiceImpl implements MyAppointmentService {

    private final ZlCleanOrderMyMapper zlCleanOrderMyMapper;

    private final ZlInvoiceMyMapper zlInvoiceMyMapper;

    private final ZlRepairorderMyMapper zlRepairorderMyMapper;

    private final ZlCleanOrderMapper cleanOrderMapper;

    @Autowired
    public MyAppointmentServiceImpl(ZlCleanOrderMyMapper zlCleanOrderMyMapper, ZlCleanOrderMapper cleanOrderMapper,ZlInvoiceMyMapper zlInvoiceMyMapper, ZlRepairorderMyMapper zlRepairorderMyMapper) {
        this.zlCleanOrderMyMapper = zlCleanOrderMyMapper;
        this.zlInvoiceMyMapper = zlInvoiceMyMapper;
        this.zlRepairorderMyMapper = zlRepairorderMyMapper;
        this.cleanOrderMapper = cleanOrderMapper;
    }

    /**
     * 获取所有清扫订单
     * @param userId  用户id
     * @param orderstatus  服务状态
     * @param pageNo  分页起始页
     * @param pageSize  每页的条数
     * @return
     */
    @Override
    public PageInfoResult cleanFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlCleanOrder> cleanorders = zlCleanOrderMyMapper.findAllByStatus(userId,orderstatus);
        PageInfo<ZlCleanOrder> pageInfo = new PageInfo<>(cleanorders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 取消清扫订单
     * @param orderid
     */
    public void canceCleanOrder(Long orderid) {
        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setOrderid(orderid);
        ZlCleanOrder cleanOrder = cleanOrderMapper.selectOne(zlCleanOrder);
        if (cleanOrder != null){
            cleanOrder.setOrderstatus((byte) -1);
            cleanOrder.setUpdatedate((int) new Date().getTime());
            cleanOrderMapper.updateByPrimaryKey(cleanOrder);
        }
    }

    //################################################################

    /**
     * 获取发票订单
     * @param userId
     * @param invoicestatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult invoiceFindAll(Long userId, Integer invoicestatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlInvoice> invoices = zlInvoiceMyMapper.findAllByUserId(userId,invoicestatus);
        PageInfo<ZlInvoice> pageInfo = new PageInfo<>(invoices);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 获取报修订单列表
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult repairFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlRepairorder> repairOrders = zlRepairorderMyMapper.findAllByUserId(userId,orderstatus);

        PageInfo<ZlRepairorder> pageInfo = new PageInfo<>(repairOrders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }



}
