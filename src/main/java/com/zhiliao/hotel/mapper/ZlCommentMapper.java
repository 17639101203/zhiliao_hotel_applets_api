package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 10:39
 * @Description:
 */

public interface ZlCommentMapper extends Mapper<ZlComment> {

    int addComment(ZlComment zlComment);

    List<ZlComment> getComments(Long userid);

}