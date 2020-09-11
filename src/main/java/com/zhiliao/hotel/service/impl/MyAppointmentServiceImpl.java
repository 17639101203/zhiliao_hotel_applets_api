package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.myAppointment.dto.*;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.myAppointment.result.ZlServiceorderResult;
import com.zhiliao.hotel.controller.myOrder.vo.OrderVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageInfoResult cleanFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCleanOrder> cleanorders = myAppointmentMapper.findAllClean(userId, orderstatus, hotelId);
        PageInfo<ZlCleanOrder> zlCleanOrderPageInfo = new PageInfo<ZlCleanOrder>(cleanorders);

        List<ZlCleanOrderDTO> zlCleanOrderDTOS = new LinkedList<>();
        for (ZlCleanOrder cleanorder : cleanorders) {
            ZlCleanOrderDTO zlCleanOrderDTO = new ZlCleanOrderDTO();
            if (cleanorder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(cleanorder, zlCleanOrderDTO);
                zlCleanOrderDTO.setStatusmessage("已取消");
            }
            if (cleanorder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(cleanorder, zlCleanOrderDTO);
                zlCleanOrderDTO.setStatusmessage("等待退房");
            }
            if (cleanorder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(cleanorder, zlCleanOrderDTO);
                zlCleanOrderDTO.setStatusmessage("已完成");
            }
            if (cleanorder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(cleanorder, zlCleanOrderDTO);
                zlCleanOrderDTO.setStatusmessage("已接单");
            }
            zlCleanOrderDTOS.add(zlCleanOrderDTO);
        }

        PageInfo<ZlCleanOrderDTO> pageInfo = new PageInfo<>(zlCleanOrderDTOS);
        pageInfo.setTotal(zlCleanOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlCleanOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlCleanOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlCleanOrderPageInfo.getTotal() % zlCleanOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlCleanOrderPageInfo.getTotal() / zlCleanOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));
        return PageInfoResult.getPageInfoResult(pageInfo);
    }


    /**
     * 获取发票订单
     *
     * @param userId
     * @param invoiceStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult invoiceFindAll(Long userId, Byte invoiceStatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<InvoiceOrderVO> invoiceOrders = myAppointmentMapper.findAllInvoice(userId, invoiceStatus, hotelId);
        PageInfo<InvoiceOrderVO> invoiceOrderVOPageInfo = new PageInfo<InvoiceOrderVO>(invoiceOrders);

        for (InvoiceOrderVO invoiceOrderVO : invoiceOrders) {
            ZlHotel zlHotel = zlHotelMapper.getById(hotelId);
            invoiceOrderVO.setHotelname(zlHotel.getHotelName());
        }

        List<InvoiceOrderDTO> invoiceOrderDTOS = new LinkedList<>();
        for (InvoiceOrderVO invoiceOrderVO : invoiceOrders) {
            InvoiceOrderDTO invoiceOrderDTO = new InvoiceOrderDTO();
            if (invoiceOrderVO.getInvoicestatus() == -1) {
                BeanUtils.copyProperties(invoiceOrderVO, invoiceOrderDTO);
                invoiceOrderDTO.setStatusmessage("已取消");
            }
            if (invoiceOrderVO.getInvoicestatus() == 0) {
                BeanUtils.copyProperties(invoiceOrderVO, invoiceOrderDTO);
                invoiceOrderDTO.setStatusmessage("未开票");
            }
            if (invoiceOrderVO.getInvoicestatus() == 1) {
                BeanUtils.copyProperties(invoiceOrderVO, invoiceOrderDTO);
                invoiceOrderDTO.setStatusmessage("开票中");
            }
            if (invoiceOrderVO.getInvoicestatus() == 2) {
                BeanUtils.copyProperties(invoiceOrderVO, invoiceOrderDTO);
                invoiceOrderDTO.setStatusmessage("已开票");
            }
            if (invoiceOrderVO.getInvoicestatus() == 3) {
                BeanUtils.copyProperties(invoiceOrderVO, invoiceOrderDTO);
                invoiceOrderDTO.setStatusmessage("已接单");
            }
            invoiceOrderDTOS.add(invoiceOrderDTO);
        }
        PageInfo<InvoiceOrderDTO> pageInfo = new PageInfo<>(invoiceOrderDTOS);
        pageInfo.setTotal(invoiceOrderVOPageInfo.getTotal());
        pageInfo.setPageNum(invoiceOrderVOPageInfo.getPageNum());
        pageInfo.setPageSize(invoiceOrderVOPageInfo.getPageSize());
        int remainder = Math.toIntExact(invoiceOrderVOPageInfo.getTotal() % invoiceOrderVOPageInfo.getPageSize());
        int pages = Math.toIntExact(invoiceOrderVOPageInfo.getTotal() / invoiceOrderVOPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));
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
    public PageInfoResult repairFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlRepairorder> repairOrders = myAppointmentMapper.findAllRepair(userId, orderstatus, hotelId);
        PageInfo<ZlRepairorder> zlRepairorderPageInfo = new PageInfo<ZlRepairorder>(repairOrders);

        List<ZlRepairorderDTO> zlRepairorderDTOS = new LinkedList<>();
        for (ZlRepairorder zlRepairorder : repairOrders) {
            ZlRepairorderDTO zlRepairorderDTO = new ZlRepairorderDTO();
            if (zlRepairorder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlRepairorder, zlRepairorderDTO);
                zlRepairorderDTO.setStatusmessage("已取消");
            }
            if (zlRepairorder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlRepairorder, zlRepairorderDTO);
                zlRepairorderDTO.setStatusmessage("待维修");
            }
            if (zlRepairorder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlRepairorder, zlRepairorderDTO);
                zlRepairorderDTO.setStatusmessage("已完成");
            }
            if (zlRepairorder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlRepairorder, zlRepairorderDTO);
                zlRepairorderDTO.setStatusmessage("已接单");
            }
            zlRepairorderDTOS.add(zlRepairorderDTO);
        }

        PageInfo<ZlRepairorderDTO> pageInfo = new PageInfo<>(zlRepairorderDTOS);
        pageInfo.setTotal(zlRepairorderPageInfo.getTotal());
        pageInfo.setPageNum(zlRepairorderPageInfo.getPageNum());
        pageInfo.setPageSize(zlRepairorderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlRepairorderPageInfo.getTotal() % zlRepairorderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlRepairorderPageInfo.getTotal() / zlRepairorderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

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
    public PageInfoResult serviceFindAll(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlServiceorderResult> zlServiceorderResults = myAppointmentMapper.serviceFindAll(userId, orderstatus, hotelId);
        PageInfo<ZlServiceorderResult> zlServiceorderResultPageInfo = new PageInfo<ZlServiceorderResult>(zlServiceorderResults);

        List<ZlServiceorderResultDTO> zlServiceorderResultDTOS = new LinkedList<>();
        for (ZlServiceorderResult zlServiceorderResult : zlServiceorderResults) {
            ZlServiceorderResultDTO zlServiceorderResultDTO = new ZlServiceorderResultDTO();
            if (zlServiceorderResult.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlServiceorderResult, zlServiceorderResultDTO);
                zlServiceorderResultDTO.setStatusmessage("已取消");
            }
            if (zlServiceorderResult.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlServiceorderResult, zlServiceorderResultDTO);
                zlServiceorderResultDTO.setStatusmessage("待配送");
            }
            if (zlServiceorderResult.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlServiceorderResult, zlServiceorderResultDTO);
                zlServiceorderResultDTO.setStatusmessage("已完成");
            }
            if (zlServiceorderResult.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlServiceorderResult, zlServiceorderResultDTO);
                zlServiceorderResultDTO.setStatusmessage("已接单");
            }
            zlServiceorderResultDTOS.add(zlServiceorderResultDTO);
        }

        PageInfo<ZlServiceorderResultDTO> pageInfo = new PageInfo<>(zlServiceorderResultDTOS);
        pageInfo.setTotal(zlServiceorderResultPageInfo.getTotal());
        pageInfo.setPageNum(zlServiceorderResultPageInfo.getPageNum());
        pageInfo.setPageSize(zlServiceorderResultPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlServiceorderResultPageInfo.getTotal() % zlServiceorderResultPageInfo.getPageSize());
        int pages = Math.toIntExact(zlServiceorderResultPageInfo.getTotal() / zlServiceorderResultPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);

    }

    /**
     * 叫醒服务订单列表
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllWakeOrder(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlWakeOrder> wakeOrders = myAppointmentMapper.findAllWakeOrder(userId, orderstatus, hotelId);
        PageInfo<ZlWakeOrder> zlWakeOrderPageInfo = new PageInfo<ZlWakeOrder>(wakeOrders);

        List<ZlWakeOrderDTO> zlWakeOrderDTOS = new LinkedList<>();
        for (ZlWakeOrder zlWakeOrder : wakeOrders) {
            ZlWakeOrderDTO zlWakeOrderDTO = new ZlWakeOrderDTO();
            if (zlWakeOrder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlWakeOrder, zlWakeOrderDTO);
                zlWakeOrderDTO.setStatusmessage("已取消");
            }
            if (zlWakeOrder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlWakeOrder, zlWakeOrderDTO);
                zlWakeOrderDTO.setStatusmessage("待处理");
            }
            if (zlWakeOrder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlWakeOrder, zlWakeOrderDTO);
                zlWakeOrderDTO.setStatusmessage("处理完成");
            }
            if (zlWakeOrder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlWakeOrder, zlWakeOrderDTO);
                zlWakeOrderDTO.setStatusmessage("已接单");
            }
            zlWakeOrderDTOS.add(zlWakeOrderDTO);
        }

        PageInfo<ZlWakeOrderDTO> pageInfo = new PageInfo<>(zlWakeOrderDTOS);
        pageInfo.setTotal(zlWakeOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlWakeOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlWakeOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlWakeOrderPageInfo.getTotal() % zlWakeOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlWakeOrderPageInfo.getTotal() / zlWakeOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 租车服务订单列表
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllRentCarOrder(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlRentCarOrder> rentCarOrders = myAppointmentMapper.findAllRentCarOrder(userId, orderstatus, hotelId);
        PageInfo<ZlRentCarOrder> zlRentCarOrderPageInfo = new PageInfo<ZlRentCarOrder>(rentCarOrders);

        List<ZlRentCarOrderDTO> zlRentCarOrderDTOS = new LinkedList<>();
        for (ZlRentCarOrder zlRentCarOrder : rentCarOrders) {
            ZlRentCarOrderDTO zlRentCarOrderDTO = new ZlRentCarOrderDTO();
            if (zlRentCarOrder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlRentCarOrder, zlRentCarOrderDTO);
                zlRentCarOrderDTO.setStatusmessage("已取消");
            }
            if (zlRentCarOrder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlRentCarOrder, zlRentCarOrderDTO);
                zlRentCarOrderDTO.setStatusmessage("待处理");
            }
            if (zlRentCarOrder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlRentCarOrder, zlRentCarOrderDTO);
                zlRentCarOrderDTO.setStatusmessage("已租赁");
            }
            if (zlRentCarOrder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlRentCarOrder, zlRentCarOrderDTO);
                zlRentCarOrderDTO.setStatusmessage("处理完成");
            }
            if (zlRentCarOrder.getOrderstatus() == 3) {
                BeanUtils.copyProperties(zlRentCarOrder, zlRentCarOrderDTO);
                zlRentCarOrderDTO.setStatusmessage("已接单");
            }
            zlRentCarOrderDTOS.add(zlRentCarOrderDTO);
        }

        PageInfo<ZlRentCarOrderDTO> pageInfo = new PageInfo<>(zlRentCarOrderDTOS);
        pageInfo.setTotal(zlRentCarOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlRentCarOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlRentCarOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlRentCarOrderPageInfo.getTotal() % zlRentCarOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlRentCarOrderPageInfo.getTotal() / zlRentCarOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 退房服务订单
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllCheckOutOrder(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlCheckoutOrder> checkoutOrders = myAppointmentMapper.findAllCheckOutOrder(userId, orderstatus, hotelId);
        PageInfo<ZlCheckoutOrder> zlCheckoutOrderPageInfo = new PageInfo<ZlCheckoutOrder>(checkoutOrders);

        List<ZlCheckoutOrderDTO> zlCheckoutOrderDTOS = new LinkedList<>();
        for (ZlCheckoutOrder zlCheckoutOrder : checkoutOrders) {
            ZlCheckoutOrderDTO zlCheckoutOrderDTO = new ZlCheckoutOrderDTO();
            if (zlCheckoutOrder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlCheckoutOrder, zlCheckoutOrderDTO);
                zlCheckoutOrderDTO.setStatusmessage("取消退房");
            }
            if (zlCheckoutOrder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlCheckoutOrder, zlCheckoutOrderDTO);
                zlCheckoutOrderDTO.setStatusmessage("等待退房");
            }
            if (zlCheckoutOrder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlCheckoutOrder, zlCheckoutOrderDTO);
                zlCheckoutOrderDTO.setStatusmessage("完成退房");
            }
            if (zlCheckoutOrder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlCheckoutOrder, zlCheckoutOrderDTO);
                zlCheckoutOrderDTO.setStatusmessage("已接单");
            }
            zlCheckoutOrderDTOS.add(zlCheckoutOrderDTO);
        }

        PageInfo<ZlCheckoutOrderDTO> pageInfo = new PageInfo<>(zlCheckoutOrderDTOS);
        pageInfo.setTotal(zlCheckoutOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlCheckoutOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlCheckoutOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlCheckoutOrderPageInfo.getTotal() % zlCheckoutOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlCheckoutOrderPageInfo.getTotal() / zlCheckoutOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 续住服务订单
     *
     * @param userId
     * @param orderstatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult findAllContinueLiveOrder(Long userId, Byte orderstatus, Integer pageNo, Integer pageSize, Integer hotelId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ZlContinueLiveOrder> zlContinueLiveOrders = myAppointmentMapper.findAllContinueLiveOrder(userId, orderstatus, hotelId);
        PageInfo<ZlContinueLiveOrder> zlContinueLiveOrderPageInfo = new PageInfo<ZlContinueLiveOrder>(zlContinueLiveOrders);

        List<ZlContinueLiveOrderDTO> zlContinueLiveOrderDTOS = new LinkedList<>();
        for (ZlContinueLiveOrder zlContinueLiveOrder : zlContinueLiveOrders) {
            ZlContinueLiveOrderDTO zlContinueLiveOrderDTO = new ZlContinueLiveOrderDTO();
            if (zlContinueLiveOrder.getOrderstatus() == -1) {
                BeanUtils.copyProperties(zlContinueLiveOrder, zlContinueLiveOrderDTO);
                zlContinueLiveOrderDTO.setStatusmessage("已取消");
            }
            if (zlContinueLiveOrder.getOrderstatus() == 0) {
                BeanUtils.copyProperties(zlContinueLiveOrder, zlContinueLiveOrderDTO);
                zlContinueLiveOrderDTO.setStatusmessage("待处理");
            }
            if (zlContinueLiveOrder.getOrderstatus() == 1) {
                BeanUtils.copyProperties(zlContinueLiveOrder, zlContinueLiveOrderDTO);
                zlContinueLiveOrderDTO.setStatusmessage("已接单");
            }
            if (zlContinueLiveOrder.getOrderstatus() == 2) {
                BeanUtils.copyProperties(zlContinueLiveOrder, zlContinueLiveOrderDTO);
                zlContinueLiveOrderDTO.setStatusmessage("已接单");
            }
            zlContinueLiveOrderDTOS.add(zlContinueLiveOrderDTO);
        }

        PageInfo<ZlContinueLiveOrderDTO> pageInfo = new PageInfo<>(zlContinueLiveOrderDTOS);
        pageInfo.setTotal(zlContinueLiveOrderPageInfo.getTotal());
        pageInfo.setPageNum(zlContinueLiveOrderPageInfo.getPageNum());
        pageInfo.setPageSize(zlContinueLiveOrderPageInfo.getPageSize());
        int remainder = Math.toIntExact(zlContinueLiveOrderPageInfo.getTotal() % zlContinueLiveOrderPageInfo.getPageSize());
        int pages = Math.toIntExact(zlContinueLiveOrderPageInfo.getTotal() / zlContinueLiveOrderPageInfo.getPageSize());
        pageInfo.setPages(remainder == 0 ? pages : (pages + 1));

        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 各订单类型数量
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> myAppointementCount(Long userId, Integer hotelId) {

        //清扫订单数量
        int cleanOrderCount = cleanOrderMapper.selectCountClean(userId, hotelId);
        //发票订单数量
        int invoiceOrderCount = invoiceOrderMapper.selectCountInvoice(userId, hotelId);
        //报修订单数量
        int repairOrderCount = repairorderMapper.selectCountRepair(userId, hotelId);
        //客房服务订单数量
        int serviceOrderCount = serviceorderMapper.selectCountService(userId, hotelId);
        //设施订单数量
        int facilityOrderCount = facilityOrderMapper.selectCountFacility(userId, hotelId);
        //叫醒服务订单数量
        int wakeOrderCount = wakeOrderMapper.selectCountWake(userId, hotelId);
        //租车订单数量
        int rentCarOrderCount = rentCarOrderMapper.selectCountRentCar(userId, hotelId);
        //退房服务订单数量
        int checkoutOrderCount = checkoutOrderMapper.selectCountCheckOut(userId, hotelId);
        //续住服务订单
        int continueLiveOrderCount = continueLiveOrderMapper.selectCountLive(userId, hotelId);

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
            canceInvoiceOrder(orderid);
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
            repairorderMapper.removeRepairOrder(orderid, Math.toIntExact(System.currentTimeMillis() / 1000));
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
            cleanOrderMapper.removeCleanOrder(orderid, Math.toIntExact(System.currentTimeMillis() / 1000));
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
            cleanOrderMapper.removeCleanOrder(orderid, Math.toIntExact(System.currentTimeMillis() / 1000));
        }
    }

    /**
     * 发票订单取消
     *
     * @param invoiceorderid
     */
    public void canceInvoiceOrder(Long invoiceorderid) {
        ZlInvoiceOrder invoiceOrder = new ZlInvoiceOrder();
        invoiceOrder.setInvoiceorderid(invoiceorderid);
        ZlInvoiceOrder invoice = invoiceOrderMapper.selectOne(invoiceOrder);
        if (invoice != null) {
            invoiceOrderMapper.removeInvoiceOrder(invoice.getInvoiceorderid(), Math.toIntExact(System.currentTimeMillis() / 1000));
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
