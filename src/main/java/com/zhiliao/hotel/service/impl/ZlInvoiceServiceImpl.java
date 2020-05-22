package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlInvoiceMapper;
import com.zhiliao.hotel.model.ZlInvoice;
import com.zhiliao.hotel.service.ZlInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlInvoiceServiceImpl implements ZlInvoiceService {

    private final ZlInvoiceMapper mapper;

    @Autowired
    public ZlInvoiceServiceImpl(ZlInvoiceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void addInvoice(ZlInvoice Invoice) {

        Integer i = mapper.insertInvoice(Invoice);
        if(i!=1) {
            throw new RuntimeException("开票抬头新增失败,请重新再试！");
        }
    }

    @Override
    public List<Map<String,Object>> queryByUserID(Long userid) {
        List<Map<String,Object>> list = mapper.queryInvoiceByUserID(userid);
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

    @Override
    public Map<String,Object> findinvoicedetails(Long userid, Integer invoiceid) {
        return mapper.queryInvoicedetail(userid,invoiceid);
    }

}
