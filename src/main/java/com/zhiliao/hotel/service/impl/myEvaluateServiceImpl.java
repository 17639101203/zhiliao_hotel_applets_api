package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.mapper.myEvaluateMapper;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.myEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class myEvaluateServiceImpl implements myEvaluateService {

    @Autowired
    private myEvaluateMapper evaluateMapper;

    /**
     * 获取所有的评价信息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoResult listEvaluates(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ZlComment> commentList = evaluateMapper.listEvaluates(userId);
        PageInfo<ZlComment> pageInfo = new PageInfo<>(commentList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    /**
     * 评价订单详情
     * @param commentid
     * @return
     */
    @Override
    public ZlComment evaluateDetail(Integer commentid) {

        return evaluateMapper.evaluateDetail(commentid);
    }
}
