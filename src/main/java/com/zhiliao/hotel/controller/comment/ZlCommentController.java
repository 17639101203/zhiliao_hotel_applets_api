package com.zhiliao.hotel.controller.comment;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.comment.commentparm.CommentParm;
import com.zhiliao.hotel.controller.comment.vo.CommentDetailVO;
import com.zhiliao.hotel.controller.comment.vo.CommentVO;
import com.zhiliao.hotel.controller.file.UploadFileController;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.ZlCommentService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.QiniuUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Api(tags = "首页_点赞吐槽接口_章英杰")
@RestController
@RequestMapping("comment")
@Slf4j
public class ZlCommentController {

    @Autowired
    private ZlCommentService zlCommentService;

    @Autowired
    private UploadFileController uploadFileController;


    @ApiOperation(value = "添加点赞吐槽")
    @PostMapping(value = "addComment", consumes = {"multipart/*"}, headers = "content-type=multipart/form-data")
    @UserLoginToken
    public ReturnString addComment(HttpServletRequest request,
                                   CommentParm commentParm,
                                   @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {

        // 解析token获取userid
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        try {
            Map<String, Object> map = zlCommentService.addComment(userid, commentParm, multipartFile);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("添加点赞吐槽失败!");
        }
    }

    @ApiOperation(value = "获取点赞吐槽标签")
    @GetMapping("findTags/{hotelid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelid", dataType = "int", required = true, value = "酒店id")
    })
    @PassToken
    @UserLoginToken
    public ReturnString findTags(@PathVariable Integer hotelid) {
        List<Map<String, Object>> zlTagList = zlCommentService.findTags(hotelid);
        return new ReturnString<>(zlTagList);
    }

    @ApiOperation(value = "点赞吐槽详情列表获取")
    @GetMapping("findCommentList/{pageNo}/{pageSize}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "int", required = true, value = "当前页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "int", required = true, value = "每页个数")
    })
    @UserLoginToken
    public ReturnString findCommentList(HttpServletRequest request,
                                        @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        PageInfoResult<List<CommentVO>> list = zlCommentService.findComments(userid, pageNo, pageSize);
        return new ReturnString<>(list);
    }


    @ApiOperation(value = "点赞吐槽详情页信息获取")
    @GetMapping("findComment/{commentid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "commentid", dataType = "int", required = true, value = "评论吐槽id")
    })
    @UserLoginToken
    public ReturnString findComment(HttpServletRequest request, @PathVariable Integer commentid) {
        Long userid = TokenUtil.getUserId(request.getHeader("token"));
        CommentDetailVO commentDetailVO = zlCommentService.findComment(userid, commentid);
        return new ReturnString<>(commentDetailVO);
    }

}
