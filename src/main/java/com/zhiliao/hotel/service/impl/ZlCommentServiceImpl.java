package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.comment.commentparm.CommentVO;
import com.zhiliao.hotel.mapper.ZlCommentMapper;
import com.zhiliao.hotel.mapper.ZlTagMapper;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;
import com.zhiliao.hotel.service.ZlCommentService;
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
            if(comment.getTagids().contains("|")){
                // 拆解标签I
                tagids = comment.getTagids().split("|");
            }else{
                tagids = new String[1];
                tagids[0] = comment.getTagids();
            }
            // 查询标签名
            comment.setTagname(zlTagMapper.getTagName(tagids));
        }
        PageInfo<CommentVO> pageInfo = new PageInfo<>(comments);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public Map<String, Object> findComment(Long userid, Integer commentid) {
        Map<String, Object> comment = zlCommentMapper.getComment(userid, commentid);
        String[] tagids;
        // 获取标签ID
        String TagIDs = comment.get("TagIDs").toString();
        if(TagIDs.contains("|")){
            // 拆解标签I
            tagids = TagIDs.split("|");
        }else{
            tagids = new String[1];
            tagids[0] = TagIDs;
        }
        // 查询标签名
        comment.put("tagname",zlTagMapper.getTagName(tagids));
        // 修改为已读状态
        zlCommentMapper.changeReplyReadStatus(userid, commentid);
        return comment;
    }
}
