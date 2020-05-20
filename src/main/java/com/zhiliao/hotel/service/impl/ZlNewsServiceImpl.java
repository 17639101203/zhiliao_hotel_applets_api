package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlNewsMapper;
import com.zhiliao.hotel.model.ZlNews;
import com.zhiliao.hotel.service.ZlNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨讯service实现类
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlNewsServiceImpl implements ZlNewsService {

    private final ZlNewsMapper zlNewsMapper;

    @Autowired
    public ZlNewsServiceImpl(ZlNewsMapper zlNewsMapper) {
        this.zlNewsMapper = zlNewsMapper;
    }

    @Override
    public PageInfoResult findAllJiuDianId(Integer pageNo, Integer pageSize, Integer hotelID, Integer type, Integer status) {
        //分页插件 pageNum:起始页，pageSize：每页显示条数
        PageHelper.startPage(pageNo,pageSize);
        List<ZlNews> news = zlNewsMapper.findAllJiuDianId(hotelID,type,status);
        PageInfo<ZlNews> pageInfo=new PageInfo<>(news);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public ZlNews findById(Integer newsid) {
        ZlNews news = zlNewsMapper.findById(newsid);
        if (news != null && news.getHotelid() == 0){
            news.setHotelName("知了管家");
        }
        return news;

    }
}
