package com.zhiliao.hotel.controller.comment;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.model.ZlTag;
import com.zhiliao.hotel.service.ZlCommentService;
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

import java.util.List;


/**
 * @Author: Zhangyong
 * @Date: 2020/4/15 14:49
 * @Description:
 */
@Api(tags = "点赞吐槽接口")
@RestController
@RequestMapping("comment")
public class ZlGetTagsCommentController {

    private static final Logger logger = LoggerFactory.getLogger(ZlGetTagsCommentController.class);

    private ZlCommentService zlCommentService;

    @Autowired
    public ZlGetTagsCommentController(ZlCommentService zlCommentService, TokenUtil tokenUtil) {
        this.zlCommentService = zlCommentService;
    }

    @ApiOperation(value = "获取点赞吐槽标签")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "hotelID", dataType = "int", required = true, value = "酒店ID"),
    })
    @PostMapping("getTags")
    @UserLoginToken
    public ReturnString getTags(String token, Integer hotelID) {

        ZlTag zlTag = new ZlTag();
        zlTag.setHotelid(hotelID);

        List<ZlTag> zlTagList = zlCommentService.getTags(zlTag);

            return new ReturnString(zlTagList);

    }

}
