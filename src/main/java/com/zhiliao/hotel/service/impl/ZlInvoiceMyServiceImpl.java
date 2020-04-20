package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlInvoiceMapper;
import com.zhiliao.hotel.model.zlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceMyService;
import com.zhiliao.hotel.service.ZlInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ZlInvoiceMyServiceImpl implements ZlInvoiceMyService {

    @Autowired
    private ZlInvoiceMapper mapper;

    @Override
    public PageInfoResult findAllByUserId(Long userId, Integer invoicestatus, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<zlInvoice> invoices = mapper.findAllByUserId(userId,invoicestatus);
        for (int i = 0; i < invoices.size(); i++) {
            zlInvoice invoice = invoices.get(i);
            invoice.setFuwutype("发票服务");
        }
        PageInfo<zlInvoice> pageInfo = new PageInfo<>(invoices);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public zlInvoice orderDetail(Integer invoiceid) {
        return mapper.orderDetail(invoiceid);
    }
}
