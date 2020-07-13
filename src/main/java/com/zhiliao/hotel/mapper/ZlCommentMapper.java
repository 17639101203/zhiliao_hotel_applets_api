package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.comment.vo.CommentDetailVO;
import com.zhiliao.hotel.controller.comment.vo.CommentToPhpVO;
import com.zhiliao.hotel.controller.comment.vo.CommentVO;
import com.zhiliao.hotel.model.ZlComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


public interface ZlCommentMapper extends Mapper<ZlComment> {

    int addComment(ZlComment zlComment);

    List<CommentVO> getComments(Long userid);

    CommentDetailVO getComment(@Param("userid") Long userid, @Param("commentid") Integer commentid);

    void changeReplyReadStatus(@Param("userid") Long userid, @Param("commentid") Integer commentid);

    Long waitAppraiseTotal(@Param("userid") Long userid);

    void updateCommentImg(@Param("commentID") Integer commentID, @Param("imageurls") String imageurls);

    CommentToPhpVO selectToPhp(@Param("commentid") Integer commentid);
}
