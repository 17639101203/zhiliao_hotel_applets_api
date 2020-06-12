package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.mapper.ZlNewsMapper;
import com.zhiliao.hotel.model.ZlHotel;
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

    @Autowired
    private ZlNewsMapper zlNewsMapper;

    @Autowired
    private ZlHotelMapper zlHotelMapper;

    @Autowired
    public ZlNewsServiceImpl(ZlNewsMapper zlNewsMapper) {
        this.zlNewsMapper = zlNewsMapper;
    }

    @Override
    public PageInfoResult findAllHoteId(Integer hotelID, Integer pageNo, Integer pageSize) {

        //分页插件 pageNum:起始页，pageSize：每页显示条数
        PageHelper.startPage(pageNo, pageSize);
        List<ZlNews> news = zlNewsMapper.findAllJiuDianId(hotelID);
        if (news.size() != 0) {
            PageInfo<ZlNews> pageInfo = new PageInfo<>(news);
            return PageInfoResult.getPageInfoResult(pageInfo);
        }

        //分页插件 pageNum:起始页，pageSize：每页显示条数
        PageHelper.startPage(pageNo, pageSize);
        List<ZlNews> newsZl = zlNewsMapper.findAllJiuDianId(0);
        PageInfo<ZlNews> pageInfo = new PageInfo<>(newsZl);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public ZlNews findById(Integer newsid) {
        ZlNews news = zlNewsMapper.findById(newsid);
        if (news != null && news.getHotelid() == 0) {
            if (news.getType() == 1) {
                news.setHotelName("系统公告");
                return news;
            }
            if (news.getType() == 2) {
                news.setHotelName("系统新闻");
                return news;
            }
        }
        ZlHotel zlHotel = zlHotelMapper.getById(news.getHotelid());
        news.setHotelName(zlHotel.getHotelName());
        return news;

    }
}
