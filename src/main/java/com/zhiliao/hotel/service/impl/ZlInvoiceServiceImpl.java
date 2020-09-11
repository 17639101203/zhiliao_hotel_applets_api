package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.invoice.params.InvoiceOrderVO;
import com.zhiliao.hotel.controller.invoice.vo.InvoiceOrderToPhpVO;
import com.zhiliao.hotel.controller.myAppointment.dto.InvoiceOrderDTO;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.mapper.ZlInvoiceMapper;
import com.zhiliao.hotel.mapper.ZlInvoiceOrderMapper;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.model.ZlInvoiceOrder;
import com.zhiliao.hotel.service.OrderLogService;
import com.zhiliao.hotel.service.ZlInvoiceService;
import com.zhiliao.hotel.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlInvoiceServiceImpl implements ZlInvoiceService {

    @Autowired
    private ZlInvoiceMapper mapper;

    @Autowired
    private ZlInvoiceOrderMapper orderMapper;

    @Autowired
    private ZlHotelMapper zlHotelMapper;

    @Autowired
    private OrderLogService orderLogService;

    @Override
    public Map<String, Object> addInvoice(ZlInvoice invoice) {
        Map<String, Object> map = new HashMap<>();
        mapper.insertInvoice(invoice);
        map.put("invoiceID", invoice.getInvoiceid());
        map.put("invoiceType", invoice.getInvoicetype());
        return map;
    }

    @Override
    public void deleteInvoice(Integer invoiceid) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        mapper.deleteInvoice(invoiceid, updateDate);
    }


    @Override
    public PageInfoResult<List<Map<String, Object>>> queryByUserID(Long userid, Integer pageNo, Integer pageSize) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = mapper.queryInvoiceByUserID(userid);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        if (list == null) {
            throw new RuntimeException("开票抬头查询失败,请重新再试！");
        }
        return PageInfoResult.getPageInfoResult(pageInfo);
    }


    @Override
    public Map<String, Object> findinvoicedetails(Long userid, Integer invoiceid) {
        return mapper.queryInvoicedetail(userid, invoiceid);
    }


    @Override
    public String findInvoiceQrCodeUrl(Integer hotelid) {
        return mapper.queryInvoiceQrCodeUrl(hotelid);
    }

    @Override
    public void addinvoiceOrder(ZlInvoiceOrder zlInvoiceOrder) {
//        orderMapper.insertInvoiceOrder(zlInvoiceOrder);
//        orderMapper.insert(zlInvoiceOrder);
        orderMapper.insertSelective(zlInvoiceOrder);
    }

    @Override
    public void changeInvoice(ZlInvoice zlInvoice) {
        mapper.updateInvoiceMsg(zlInvoice);
    }

    @Override
    public InvoiceOrderDTO findInvoiceOrderdetail(Long invoiceorderid) {
        InvoiceOrderDTO invoiceOrderDTO = orderMapper.queryInvoiceOrderdetail(invoiceorderid);
        if (invoiceOrderDTO != null) {
            ZlHotel zlHotel = zlHotelMapper.getById(invoiceOrderDTO.getHotelid());
            invoiceOrderDTO.setHotelname(zlHotel.getHotelName());
        }
        return invoiceOrderDTO;
    }

    @Override
    public void cancelInvoiceOrder(Long invoiceorderid) {
        Integer updatedate = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlInvoiceOrder zlInvoiceOrder = new ZlInvoiceOrder();
        zlInvoiceOrder.setInvoiceorderid(invoiceorderid);
        zlInvoiceOrder = orderMapper.selectOne(zlInvoiceOrder);
        if (zlInvoiceOrder == null) {
            throw new BizException("参数有误!");
        }

        //将订单取消操作写到记录表中
        orderLogService.cancelOrderLog(zlInvoiceOrder.getInvoiceorderid(), zlInvoiceOrder.getHotelid(), zlInvoiceOrder.getCreatedate(), zlInvoiceOrder.getMoldtype());
        orderMapper.removeInvoiceOrder(invoiceorderid, updatedate);
    }

    @Override
    public void deleteInvoiceOrder(Long invoiceorderid) {
        Integer nowTime = DateUtils.javaToPhpNowDateTime();
        orderMapper.deleteInvoiceOrder(invoiceorderid, nowTime);
    }

    @Override
    public InvoiceOrderToPhpVO selectToPhp(Long invoiceorderid) {
        return orderMapper.selectToPhp(invoiceorderid);
    }

}
