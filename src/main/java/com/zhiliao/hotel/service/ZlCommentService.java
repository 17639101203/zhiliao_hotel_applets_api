package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;

import java.util.List;

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
    List<ZlTag> findTags(Integer hotelid);


    /**
     * 获取点赞吐槽列表
     */
    List<ZlComment> findComments(Long userid);
}
