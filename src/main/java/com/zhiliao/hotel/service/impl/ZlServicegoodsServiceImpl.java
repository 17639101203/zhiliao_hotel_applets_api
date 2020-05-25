package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.servicegoods.vo.ServicegoodsListVo;
import com.zhiliao.hotel.mapper.ZlServicegoodsMapper;
import com.zhiliao.hotel.service.ZlServicegoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiegege on 2020/4/23.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlServicegoodsServiceImpl implements ZlServicegoodsService {

    private final ZlServicegoodsMapper zlServicegoodsMapper;

    @Autowired
    public ZlServicegoodsServiceImpl(ZlServicegoodsMapper zlServicegoodsMapper) {
        this.zlServicegoodsMapper = zlServicegoodsMapper;
    }

    @Override
    public List<String> findServicegoodsCategory(Integer hotelId, Integer belongModule) {
        return zlServicegoodsMapper.findServicegoodsCategory(hotelId, belongModule);
    }

    @Override
    public PageInfoResult findServicegoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName, String keyword) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<ServicegoodsListVo> dataList = zlServicegoodsMapper.findServicegoodsList(hotelId, belongModule, categoryName, keyword);
        PageInfo<ServicegoodsListVo> pageInfo = new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public ServicegoodsListVo findServicegoodsDetail(Integer goodsId) {
        ServicegoodsListVo servicegoodsDetail = zlServicegoodsMapper.findServicegoodsDetail(goodsId);
        if (servicegoodsDetail != null) {
            // 更新 浏览量+1
            zlServicegoodsMapper.updateServicegoodsVisitCount(goodsId);
        }
        return servicegoodsDetail;
    }
}