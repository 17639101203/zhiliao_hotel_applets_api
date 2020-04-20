package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.zlNews;
import com.zhiliao.hotel.service.zlNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨讯service实现类
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class zlNewsServiceImpl implements zlNewsService {

    @Autowired
    private com.zhiliao.hotel.mapper.zlNewsMapper zlNewsMapper;


    @Override
    public PageInfoResult findAllJiuDianId(Integer pageNo, Integer pageSize, Integer hotelID, Integer type, Integer status) {
        //分页插件 pageNum:起始页，pageSize：每页显示条数
        PageHelper.startPage(pageNo,pageSize);
        List<zlNews> news = zlNewsMapper.findAllJiuDianId(hotelID,type,status);
        PageInfo<zlNews> pageInfo=new PageInfo<>(news);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public zlNews findById(Integer newsid) {
        return zlNewsMapper.findById(newsid);
    }
}
