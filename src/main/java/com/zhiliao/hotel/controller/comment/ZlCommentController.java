package com.zhiliao.hotel.controller.comment;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.comment.commentparm.CommentParm;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;
import com.zhiliao.hotel.service.ZlCommentService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 10:32
 * @Description:
 */
@Api(tags = "章英杰_点赞吐槽接口_首页")
@RestController
@RequestMapping("comment")
@Slf4j
public class ZlCommentController {

    @Autowired
    private ZlCommentService zlCommentService;


    @ApiOperation(value = "添加点赞吐槽")
    @PostMapping("addComment")
    @UserLoginToken
    public ReturnString addComment(HttpServletRequest request, CommentParm commentParm) {
        ZlComment zlComment = new ZlComment();
        zlComment.setHotelid(commentParm.getHotelID());   //酒店ID
        zlComment.setUserid(TokenUtil.getUserId(request.getHeader("token")));   //用户ID  根据token获取userId
        zlComment.setEvaluation((byte) commentParm.getEvaluation().intValue());   //评论等级 1好评 2中评 3差评
        zlComment.setTagids(commentParm.getTagIDs());   //评论标签ID 多个用丨隔开
        zlComment.setContent(commentParm.getContent());   //评论内容
        Integer nowtime = DateUtils.javaToPhpNowDateTime();
        zlComment.setCreatedate(nowtime);  //添加时间
        zlComment.setUpdatedate(nowtime);  //更新时间
        Integer res = zlCommentService.addComment(zlComment);
        if (res > 0) {
            return new ReturnString(0, "评价成功");
        } else {
            log.error("评价失败");
            return new ReturnString(-1, "评价失败");
        }

    }

    @ApiOperation(value = "获取点赞吐槽标签")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelID", dataType = "int", required = true, value = "酒店ID"),
    })
    @GetMapping("findTags/{hotelid}")
    @PassToken
    public ReturnString findTags(@PathVariable Integer hotelid) {
        List<ZlTag> zlTagList = zlCommentService.findTags(hotelid);
        return new ReturnString(zlTagList);
    }

    @ApiOperation(value = "点赞吐槽详情列表获取(待修改)")
    @GetMapping("findCommentList")
    @UserLoginToken
    public ReturnString findCommentList(HttpServletRequest request) {
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        List<ZlComment> list = zlCommentService.findComments(userid);
        return new ReturnString(list);
    }

    @ApiOperation(value = "点赞吐槽详情列表页信息获取(待修改)")
    @GetMapping("findComment")
    @UserLoginToken
    public ReturnString findComment(HttpServletRequest request) {

        return new ReturnString(null);
    }

}
