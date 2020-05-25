package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.comment.commentparm.CommentVO;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;

import java.util.List;
import java.util.Map;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/10 16:03
 * @Description:
 */
public interface ZlCommentService {

    /**
        添加点赞吐槽
     */
    Integer addComment(ZlComment zlComment);

    /**
        获取点赞吐槽标签
    */
    List<Map<String,Object>> findTags(Integer hotelid);


    /**
     * 获取点赞吐槽列表
     */
    PageInfoResult<List<CommentVO>> findComments(Long userid, Integer pageNo, Integer pageSize);


    /**
     * 获取点赞吐槽详情页
     */
    Map<String,Object> findComment(Long userid,Integer commentid);
}
