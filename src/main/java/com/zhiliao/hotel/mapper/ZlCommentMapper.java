package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlComment;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 10:39
 * @Description:
 */

public interface ZlCommentMapper extends Mapper<ZlComment> {

    int addComment(ZlComment zlComment);

}