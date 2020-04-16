package com.zhiliao.hotel.service.impl;

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
    public List<zlNews> findAllJiuDianId(Integer hotelID, Integer type, Integer status) {
        return zlNewsMapper.findAllJiuDianId(hotelID,type,status);
    }

    @Override
    public zlNews findById(Integer newsid) {
        return zlNewsMapper.findById(newsid);
    }
}
