package com.zhiliao.hotel.controller.comment;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.ZlCommentService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 10:32
 * @Description:
 */
@Api(tags = "点赞吐槽接口")
@RestController
@RequestMapping("comment")
public class ZlCommentController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCommentController.class);

    private final ZlCommentService zlCommentService;

    @Autowired
    public ZlCommentController(ZlCommentService zlCommentService) {
        this.zlCommentService = zlCommentService;
    }

    @ApiOperation(value = "添加点赞吐槽")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelID", dataType = "int", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "evaluation", dataType = "int", required = true, value = "评价等级"),
            @ApiImplicitParam(paramType = "query", name = "tagIDs", dataType = "String", required = false, value = "评价标签ID"),
            @ApiImplicitParam(paramType = "query", name = "content", dataType = "int", required = false, value = "评价内容")
    })
    @PostMapping("addComment")
    @UserLoginToken
    public ReturnString addComment(Integer hotelID, String token, Integer evaluation, String tagIDs, String content) {
//        logger.info("点赞吐槽 "+" "+hotelID+" "+userID+" "+evaluation+" "+tagIDs+" "+level+" "+content);
        ZlComment zlComment = new ZlComment();
        zlComment.setHotelid(hotelID);   //酒店ID
        zlComment.setUserid(TokenUtil.getUserId(token));   //用户ID  根据token获取userId
        zlComment.setEvaluation((byte) evaluation.intValue());   //评论等级 1好评 2中评 3差评
        zlComment.setTagids(tagIDs);   //评论标签ID 多个用丨隔开
        zlComment.setContent(content);   //评论内容
        //zlComment.setType((byte) 0);   //评论类型 默认点赞吐槽为0
        //zlComment.setCommentstatus((byte) 0);   //评论状态 0:未通过 1:已通过
        //zlComment.setIsdelete(false);   //删除状态 0:正常 1:删除
        zlComment.setCreatedate(DateUtils.javaToPhpNowDateTime());  //添加时间
        //zlComment.setUpdatedate(Integer.valueOf(getSystemTime()));  //更新时间

        Integer res = zlCommentService.addComment(zlComment);

        if (res > 0) {
            return new ReturnString(0, "评价成功");
        } else {
            logger.error("评价失败");
            return new ReturnString(-1, "评价失败");
        }

    }

}
