package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlInvoiceMyMapper;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZlInvoiceMyServiceImpl implements ZlInvoiceMyService {

    @Autowired
    private ZlInvoiceMyMapper mapper;

    @Override
    public PageInfoResult findAllByUserId(Long userId, Integer invoicestatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlInvoice> invoices = mapper.findAllByUserId(userId,invoicestatus);
        for (int i = 0; i < invoices.size(); i++) {
            ZlInvoice invoice = invoices.get(i);
            invoice.setFuwutype("发票服务");
        }
        PageInfo<ZlInvoice> pageInfo = new PageInfo<>(invoices);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public ZlInvoice orderDetail(Integer invoiceid) {
        return mapper.orderDetail(invoiceid);
    }
}
