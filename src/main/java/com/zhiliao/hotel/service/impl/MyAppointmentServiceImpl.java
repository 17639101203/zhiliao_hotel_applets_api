package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.myAppointment.result.ZlServiceorderResult;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlCleanOrder;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.model.ZlServiceorder;
import com.zhiliao.hotel.service.MyAppointmentService;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
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

    private final ZlCleanOrderMapper cleanOrderMapper;

    private final ZlInvoiceMapper invoiceMapper;

    @Autowired
    private ZlHotelFacilityOrderService facilityOrderService;

    @Autowired
    private ZlServiceorderMapper serviceorderMapper;
    @Autowired
    private ZlRepairorderMapper repairorderMapper;
    @Autowired
    private MyAppointmentMapper myAppointmentMapper;


    @Autowired
    public MyAppointmentServiceImpl(ZlInvoiceMapper invoiceMapper, ZlCleanOrderMapper cleanOrderMapper) {
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
        List<ZlCleanOrder> cleanorders = myAppointmentMapper.findAllClean(userId,orderstatus);
        PageInfo<ZlCleanOrder> pageInfo = new PageInfo<>(cleanorders);
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
        List<ZlInvoice> invoices = myAppointmentMapper.findAllInvoice(userId,invoicestatus);
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
        List<ZlRepairorder> repairOrders = myAppointmentMapper.findAllRepair(userId,orderstatus);
        PageInfo<ZlRepairorder> pageInfo = new PageInfo<>(repairOrders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 客房服务订单类型
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult serviceFindAll(Long userId, Integer orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlServiceorderResult> results = myAppointmentMapper.serviceFindAll(userId,orderstatus);
        PageInfo<ZlServiceorderResult> pageInfo = new PageInfo<>(results);
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
        }else if (orderServiceType == 4){
            facilityOrderService.cancelFacilityOrder(orderid);
        }
        else {
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
        ZlRepairorder repairorder = repairorderMapper.selectOne(zlRepairorder);
        if (repairorder != null){
            repairorder.setOrderstatus((byte) -1);
            repairorder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            repairorderMapper.updateByPrimaryKeySelective(repairorder);
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
            cleanOrder.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            cleanOrderMapper.updateByPrimaryKeySelective(cleanOrder);
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
            invoice.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            invoiceMapper.updateByPrimaryKeySelective(invoice);
        }
    }


    /**
     * 客房服务订单取消
     * @param orderid
     */
    public void canceServiceOrder(Long orderid){
        ZlServiceorder serviceorder = new ZlServiceorder();
        serviceorder.setOrderid(orderid);
        ZlServiceorder order = serviceorderMapper.selectOne(serviceorder);
        if (serviceorder != null){
            order.setOrderstatus((byte) -1);
            order.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            serviceorderMapper.updateByPrimaryKeySelective(order);
        }

    }

}
