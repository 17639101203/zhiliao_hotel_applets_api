package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlComment;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/10 16:03
 * @Description:
 */
public interface ZlCommentService {
    /*
        点赞吐槽
     */
    public Integer addComment(ZlComment zlComment);
}
