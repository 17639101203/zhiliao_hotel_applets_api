package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.MyAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private final ZlInvoiceMapper invoiceMapper;


    @Autowired
    public MyAppointmentServiceImpl(ZlCleanOrderMyMapper zlCleanOrderMyMapper,ZlInvoiceMapper invoiceMapper, ZlCleanOrderMapper cleanOrderMapper,ZlInvoiceMyMapper zlInvoiceMyMapper, ZlRepairorderMyMapper zlRepairorderMyMapper) {
        this.zlCleanOrderMyMapper = zlCleanOrderMyMapper;
        this.zlInvoiceMyMapper = zlInvoiceMyMapper;
        this.zlRepairorderMyMapper = zlRepairorderMyMapper;
        this.cleanOrderMapper = cleanOrderMapper;
        this.invoiceMapper = invoiceMapper;
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
        List<Map<String,Object>> cleanorders = zlCleanOrderMyMapper.findAllByStatus(userId,orderstatus);
        List<Map<String,Object>> orders = new ArrayList<>();
        for (int i = 0; i < cleanorders.size(); i++) {
            Map<String, Object> map = cleanorders.get(i);
            map.put("orderServiceType",1);
            orders.add(map);
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orders);
        return PageInfoResult.getPageInfoResult(pageInfo);
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
        List<Map<String,Object>> invoices = zlInvoiceMyMapper.findAllByUserId(userId,invoicestatus);
        List<Map<String,Object>> orders = new ArrayList<>();
        for (int i = 0; i < invoices.size(); i++) {
            Map<String, Object> map = invoices.get(i);
            map.put("orderServiceType",2);
            orders.add(map);
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(invoices);
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
        List<Map<String,Object>> repairOrders = zlRepairorderMyMapper.findAllByUserId(userId,orderstatus);
        List<Map<String,Object>> orders = new ArrayList<>();
        for (int i = 0; i < repairOrders.size(); i++) {
            Map<String, Object> map = repairOrders.get(i);
            map.put("orderServiceType",3);
            orders.add(map);
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(orders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 取消订单接口
     * @param orderid
     * @param orderServiceType
     */
    @Override
    public void cancelOrder(Long orderid, Integer orderServiceType) {
        if (orderServiceType == 1){
            canceCleanOrder(orderid);
        }else if (orderServiceType == 2){
            canceInvoiceOrder(Math.toIntExact(orderid));
        }else if (orderServiceType == 3){
            cancelRepairOrder(orderid);
        }else {
            new RuntimeException("订单标识不符");
        }
    }

    /**
     * 报修订单取消
     * @param orderid
     */
    public void cancelRepairOrder(Long orderid) {
        ZlRepairorder zlRepairorder = new ZlRepairorder();
        zlRepairorder.setOrderid(orderid);
        ZlRepairorder repairorder = zlRepairorderMyMapper.selectOne(zlRepairorder);
        if (repairorder != null){
            repairorder.setOrderstatus((byte) -1);
            repairorder.setUpdatedate((int) new Date().getTime());
            zlRepairorderMyMapper.updateByPrimaryKey(repairorder);
        }
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
    /**
     * 发票订单取消
     * @param invoiceid
     */
    public void canceInvoiceOrder(Integer invoiceid) {
        ZlInvoice zlInvoice = new ZlInvoice();
        zlInvoice.setInvoiceid(invoiceid);
        ZlInvoice invoice = invoiceMapper.selectOne(zlInvoice);
        if (invoice != null){
            invoice.setInvoicestatus((byte) -1);
            invoice.setUpdatedate((int) new Date().getTime());
            invoiceMapper.updateByPrimaryKey(invoice);
        }
    }

}
