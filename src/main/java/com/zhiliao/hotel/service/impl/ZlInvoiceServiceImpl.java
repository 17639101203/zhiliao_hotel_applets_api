package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
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

    @Autowired
    private  ZlInvoiceMapper mapper;



    @Override
    @Transactional
    public void addInvoice(ZlInvoice Invoice) {

        Integer i = mapper.insertInvoice(Invoice);
        if(i!=1) {
            throw new RuntimeException("开票抬头新增失败,请重新再试！");
        }
    }

    @Override
    public PageInfoResult<List<Map<String,Object>>> queryByUserID(Long userid,Integer pageNo,Integer pageSize) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String,Object>> list = mapper.queryInvoiceByUserID(userid);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(list);
        if(list==null){
            throw new RuntimeException("开票抬头查询失败,请重新再试！");
        }
        return PageInfoResult.getPageInfoResult(pageInfo);
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

    @Override
    public Map<String, Object> findInvoiceQrCodeUrl(Integer hotelid) {
        return mapper.queryInvoiceQrCodeUrl(hotelid);
    }

}
