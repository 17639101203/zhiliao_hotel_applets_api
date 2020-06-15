package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.comment.vo.CommentDetailVO;
import com.zhiliao.hotel.controller.comment.vo.CommentVO;
import com.zhiliao.hotel.mapper.ZlCommentMapper;
import com.zhiliao.hotel.mapper.ZlTagMapper;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.ZlCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public List<Map<String,Object>> findTags(Integer hotelid) {
        return zlTagMapper.getTags(hotelid);
    }

    
    
    @Override
    public PageInfoResult<List<CommentVO>> findComments(Long userid,Integer pageNo,Integer pageSize) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<CommentVO> comments = zlCommentMapper.getComments(userid);
        for (CommentVO comment : comments) {
            String[] tagids;
            if (StringUtils.isNoneBlank(comment.getTagids())){
                if (comment.getTagids().contains("|")) {
                    // 拆解标签I
                    tagids = comment.getTagids().split("|");
                } else {
                    tagids = new String[1];
                    tagids[0] = comment.getTagids();
                }
                // 查询标签名
                comment.setTagname(zlTagMapper.getTagName(tagids));
            }
        }
        PageInfo<CommentVO> pageInfo = new PageInfo<>(comments);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public CommentDetailVO findComment(Long userid, Integer commentid) {
        CommentDetailVO commentDetailVO = zlCommentMapper.getComment(userid, commentid);
        String[] tagids;
        if (StringUtils.isNoneBlank(commentDetailVO.getTagids())){
            if (commentDetailVO.getTagids().contains("|")) {
                // 拆解标签I
                tagids = commentDetailVO.getTagids().split("|");
            } else {
                tagids = new String[1];
                tagids[0] = commentDetailVO.getTagids();
            }
            // 查询标签名
            commentDetailVO.setTagname(zlTagMapper.getTagName(tagids));
        }
        // 修改为已读状态
        zlCommentMapper.changeReplyReadStatus(userid, commentid);
        return commentDetailVO;
    }
    
    @Override
    public Long waitAppraiseTotal(Long userid){
        return zlCommentMapper.waitAppraiseTotal(userid);
    }
    
}
