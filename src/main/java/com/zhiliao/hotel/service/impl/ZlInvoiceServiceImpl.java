package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlInvoiceMapper;
import com.zhiliao.hotel.model.zlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ZlInvoiceServiceImpl implements ZlInvoiceService {

    @Autowired
    private ZlInvoiceMapper mapper;



    @Override
    @Transactional
    public void addInvoice(zlInvoice Invoice) {

        Integer i = mapper.insertInvoice(Invoice);
        if(i!=1) {
            throw new RuntimeException("开票抬头新增失败,请重新再试！");
        }
    }

    @Override
    public List<zlInvoice> queryByUserID(Long userid) {
        List<zlInvoice> list = mapper.queryInvoiceByUserID(userid);
        if(list==null){
            throw new RuntimeException("开票抬头查询失败,请重新再试！");
        }
        return list;
    }

    @Override
    public void deleteInvoice(Long userid, Integer invoiceid) {
        Integer i = mapper.deleteInvoice(userid,invoiceid);
        if(i!=1) {
            throw new RuntimeException("开票抬头删除失败,请重新再试！");
        }
    }
}
