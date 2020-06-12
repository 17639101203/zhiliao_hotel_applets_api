package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.myAppointment.result.ZlServiceorderResult;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 我的预约服务订单
 *
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
    private ZlInvoiceOrderMapper invoiceOrderMapper;
    @Autowired
    private ZlHotelFacilityOrderMapper facilityOrderMapper;
    @Autowired
    private ZlWakeOrderMapper wakeOrderMapper;
    @Autowired
    private ZlRentCarOrderMapper rentCarOrderMapper;
    @Autowired
    private ZlContinueLiveOrderMapper continueLiveOrderMapper;
    @Autowired
    private ZlCheckoutOrderMapper checkoutOrderMapper;
    @Autowired
    private ZlWakeOrderService wakeOrderService;
    @Autowired
    private ZlRentCarGoodsService rentCarGoodsService;
    @Autowired
    private HotelLiveOrderService hotelLiveOrderService;

    @Autowired
    private ZlHotelMapper zlHotelMapper;


    @Autowired
    public MyAppointmentServiceImpl(ZlInvoiceMapper invoiceMapper, ZlCleanOrderMapper cleanOrderMapper) {
        this.cleanOrderMapper = cleanOrderMapper;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * 获取所有清扫订单
     *
     * @param userId      用户id
     * @param orderstatus 服务状态
     * @param pageNo      分页起始页
     * @param pageSize    每页的条数
     * @return
     */
    @Override
    public PageInfoResult cleanFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCleanOrder> cleanorders = myAppointmentMapper.findAllClean(userId, orderstatus);
        PageInfo<ZlCleanOrder> pageInfo = new PageInfo<>(cleanorders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }


    /**
     * 获取发票订单
     *
     * @param userId
     * @param invoicestatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult invoiceFindAll(Long userId, Byte invoicestatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<InvoiceOrderVO> invoiceOrders = myAppointmentMapper.findAllInvoice(userId, invoicestatus);
        for (InvoiceOrderVO invoiceOrderVO : invoiceOrders) {
            ZlHotel zlHotel = zlHotelMapper.getById(invoiceOrderVO.getHotelid());
            invoiceOrderVO.setHotelname(zlHotel.getHotelName());
        }
        PageInfo<InvoiceOrderVO> pageInfo = new PageInfo<>(invoiceOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }


    /**
     * 获取报修订单列表
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult repairFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlRepairorder> repairOrders = myAppointmentMapper.findAllRepair(userId, orderstatus);
        PageInfo<ZlRepairorder> pageInfo = new PageInfo<>(repairOrders);

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 客房服务订单类型
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult serviceFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlServiceorderResult> results = myAppointmentMapper.serviceFindAll(userId, orderstatus);
        PageInfo<ZlServiceorderResult> pageInfo = new PageInfo<>(results);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 叫醒服务订单列表
     *
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllWakeOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlWakeOrder> wakeOrders = myAppointmentMapper.findAllWakeOrder(userId, orderStatus);
        PageInfo<ZlWakeOrder> pageInfo = new PageInfo<>(wakeOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 租车服务订单列表
     *
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllRentCarOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlRentCarOrder> rentCarOrders = myAppointmentMapper.findAllRentCarOrder(userId, orderStatus);
        PageInfo<ZlRentCarOrder> pageInfo = new PageInfo<>(rentCarOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 退房服务订单
     *
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllCheckOutOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCheckoutOrder> checkoutOrders = myAppointmentMapper.findAllCheckOutOrder(userId, orderStatus);
        PageInfo<ZlCheckoutOrder> pageInfo = new PageInfo<>(checkoutOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 续住服务订单
     *
     * @param userId
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllContinueLiveOrder(Long userId, Byte orderStatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlContinueLiveOrder> checkoutOrders = myAppointmentMapper.findAllContinueLiveOrder(userId, orderStatus);
        PageInfo<ZlContinueLiveOrder> pageInfo = new PageInfo<>(checkoutOrders);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 各订单类型数量
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> myAppointementCount(Long userId) {

        //清扫订单数量
        int cleanOrderCount = cleanOrderMapper.selectCountClean(userId);
        //发票订单数量
        int invoiceOrderCount = invoiceOrderMapper.selectCountInvoice(userId);
        //报修订单数量
        int repairOrderCount = repairorderMapper.selectCountRepair(userId);
        //客房服务订单数量
        int serviceOrderCount = serviceorderMapper.selectCountService(userId);
        //设施订单数量
        int facilityOrderCount = facilityOrderMapper.selectCountFacility(userId);
        //叫醒服务订单数量
        int wakeOrderCount = wakeOrderMapper.selectCountWake(userId);
        //租车订单数量
        int rentCarOrderCount = rentCarOrderMapper.selectCountRentCar(userId);
        //退房服务订单数量
        int checkoutOrderCount = checkoutOrderMapper.selectCountCheckOut(userId);
        //续住服务订单
        int continueLiveOrderCount = continueLiveOrderMapper.selectCountLive(userId);

        Map<String, Integer> map = new HashMap<>();
        map.put("cleanOrderCount", cleanOrderCount);
        map.put("invoiceOrderCount", invoiceOrderCount);
        map.put("repairOrderCount", repairOrderCount);
        map.put("serviceOrderCount", serviceOrderCount);
        map.put("wakeOrderCount", wakeOrderCount);
        map.put("rentCarOrderCount", rentCarOrderCount);
        map.put("checkoutOrderCount", checkoutOrderCount);
        map.put("continueLiveOrderCount", continueLiveOrderCount);
        map.put("facilityOrderCount", facilityOrderCount);
        return map;
    }


    /**
     * 取消订单接口
     *
     * @param orderid
     * @param orderServiceType
     */
    @Override
    public void cancelOrder(Long orderid, Integer orderServiceType) {
        if (orderServiceType == 1) {
            canceCleanOrder(orderid);
        } else if (orderServiceType == 2) {
            canceInvoiceOrder(Math.toIntExact(orderid));
        } else if (orderServiceType == 3) {
            cancelRepairOrder(orderid);
        } else if (orderServiceType == 4) {
            facilityOrderService.cancelFacilityOrder(orderid);
        } else if (orderServiceType == 5) {
            canceServiceOrder(orderid);
        } else if (orderServiceType == 6) {
            wakeOrderService.cancelWakeOrder(orderid);
        } else if (orderServiceType == 7) {
            rentCarGoodsService.cancelRentCarOrder(orderid);
        } else if (orderServiceType == 8) {
            hotelLiveOrderService.cancelCheckoutOrder(orderid);
        } else if (orderServiceType == 9) {
            hotelLiveOrderService.cancelContinueLiveOrder(orderid);
        } else {
            new RuntimeException("订单标识不符");
        }
    }


    /**
     * 报修订单取消
     *
     * @param orderid
     */
    public void cancelRepairOrder(Long orderid) {
        ZlRepairorder zlRepairorder = new ZlRepairorder();
        zlRepairorder.setOrderid(orderid);
        ZlRepairorder repairorder = repairorderMapper.selectOne(zlRepairorder);
        if (repairorder != null) {
            repairorderMapper.removeRepairOrder(repairorder.getSerialnumber(), Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }

    /**
     * 取消
     *
     * @param orderid
     */
    public void canceWakeOrder(Long orderid) {
        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setOrderid(orderid);
        ZlCleanOrder cleanOrder = cleanOrderMapper.selectOne(zlCleanOrder);
        if (cleanOrder != null) {
            cleanOrderMapper.removeCleanOrder(cleanOrder.getSerialnumber(), Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }

    /**
     * 取消清扫订单
     *
     * @param orderid
     */
    public void canceCleanOrder(Long orderid) {
        ZlCleanOrder zlCleanOrder = new ZlCleanOrder();
        zlCleanOrder.setOrderid(orderid);
        ZlCleanOrder cleanOrder = cleanOrderMapper.selectOne(zlCleanOrder);
        if (cleanOrder != null) {
            cleanOrderMapper.removeCleanOrder(cleanOrder.getSerialnumber(), Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }

    /**
     * 发票订单取消
     *
     * @param invoiceid
     */
    public void canceInvoiceOrder(Integer invoiceid) {
        ZlInvoiceOrder invoiceOrder = new ZlInvoiceOrder();
        invoiceOrder.setInvoiceid(invoiceid);
        ZlInvoiceOrder invoice = invoiceOrderMapper.selectOne(invoiceOrder);
        if (invoice != null) {
            invoiceOrderMapper.removeInvoiceOrder(invoice.getInvoiceordernumber(), Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }


    /**
     * 客房服务订单取消
     *
     * @param orderid
     */
    public void canceServiceOrder(Long orderid) {
        ZlServiceorder serviceorder = new ZlServiceorder();
        serviceorder.setOrderid(orderid);
        ZlServiceorder order = serviceorderMapper.selectOne(serviceorder);
        if (serviceorder != null) {
            order.setOrderstatus((byte) -1);
            order.setUpdatedate(Math.toIntExact(System.currentTimeMillis() / 1000));
            serviceorderMapper.updateOrderStatusById(orderid, Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }
}
