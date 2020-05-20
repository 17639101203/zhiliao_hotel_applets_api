package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlCommentMapper;
import com.zhiliao.hotel.mapper.ZlTagMapper;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;
import com.zhiliao.hotel.service.ZlCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zyj
 * @Date: 2020/4/14 10:35
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlCommentServiceImpl implements ZlCommentService {


    @Autowired
    private ZlCommentMapper zlCommentMapper;

    @Autowired
    private ZlTagMapper zlTagMapper;

    @Override
    public Integer addComment(ZlComment zlComment) {
        return zlCommentMapper.addComment(zlComment);
    }

    @Override
    public List<ZlTag> findTags(Integer hotelid) {
        return zlTagMapper.getTags(hotelid);
    }

    @Override
    public List<ZlComment> findComments(Long userid) {
        return zlCommentMapper.getComments(userid);
    }
}
