package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.comment.commentparm.CommentVO;
import com.zhiliao.hotel.model.ZlComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


public interface ZlCommentMapper extends Mapper<ZlComment> {

    int addComment(ZlComment zlComment);

    List<CommentVO> getComments(Long userid);

    Map<String,Object> getComment(@Param("userid") Long userid,@Param("commentid") Integer commentid);

    void changeReplyReadStatus(@Param("userid") Long userid,@Param("commentid") Integer commentid);
    
    Long waitAppraiseTotal(@Param("userid") Long userid);
}
