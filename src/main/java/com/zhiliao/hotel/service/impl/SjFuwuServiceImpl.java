package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.fuwu.vo.FuwuListVo;
import com.zhiliao.hotel.mapper.SjFuwuMapper;
import com.zhiliao.hotel.service.SjFuwuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiegege on 2020/4/11.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class SjFuwuServiceImpl implements SjFuwuService {

    private SjFuwuMapper sjFuwuMapper;

    @Autowired
    public SjFuwuServiceImpl(SjFuwuMapper sjFuwuMapper) {
        this.sjFuwuMapper = sjFuwuMapper;
    }

    @Override
    public List<FuwuListVo> findFuwuList(Integer jiuDianId) {
        return sjFuwuMapper.findFuwuList(jiuDianId);
    }

    @Override
    public FuwuListVo findFuwuDetail(Integer fuwuId) {
        return sjFuwuMapper.findFuwuDetail(fuwuId);
    }
}