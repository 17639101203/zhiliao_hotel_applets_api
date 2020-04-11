package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.SjCbNewsMapper;
import com.zhiliao.hotel.model.SjCbNews;
import com.zhiliao.hotel.service.SjCbNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨讯service实现类
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SjCbNewsServiceImpl implements SjCbNewsService {

    @Autowired
    private SjCbNewsMapper sjCbNewsMapper;


    @Override
    public List<SjCbNews> findAllJiuDianId(Integer jiudianid) {
        return sjCbNewsMapper.findAllJiuDianId(jiudianid);
    }

    @Override
    public SjCbNews findById(Integer id) {
        return sjCbNewsMapper.findById(id);
    }
}
