package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.comment.commentparm.CommentVO;
import com.zhiliao.hotel.model.ZlComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;



public interface ZlCommentMapper extends Mapper<ZlComment> {

    int addComment(ZlComment zlComment);

    List<CommentVO> getComments(Long userid);

    List<String> getTagName(@Param("tagids") String[] tagids);

}