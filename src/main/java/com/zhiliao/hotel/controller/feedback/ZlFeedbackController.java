package com.zhiliao.hotel.controller.feedback;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.feedback.dto.ZlFeedbackDTO;
import com.zhiliao.hotel.model.ZlFeedbackType;
import com.zhiliao.hotel.service.ZlFeedbackService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-18 17:28
 **/
@Api(tags = "用户反馈接口_姬慧慧")
@RestController
@RequestMapping("zlFeedback")
public class ZlFeedbackController {

    @Autowired
    private ZlFeedbackService zlFeedbackService;

    @ApiOperation(value = "获取反馈类型列表_姬慧慧")
    @UserLoginToken
    @GetMapping("getFeedbackTypeList")
    public ReturnString getFeedbackTypeList() {

        try {
            List<ZlFeedbackType> zlFeedbackTypeList = zlFeedbackService.getFeedbackTypeList();
            return new ReturnString(zlFeedbackTypeList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户提交反馈_姬慧慧")
    @UserLoginToken
    @PostMapping("addFeedback")
    public ReturnString addFeedback(HttpServletRequest httpServletRequest, @Validated @RequestBody ZlFeedbackDTO zlFeedbackDTO) {

        //获取用户id
        String token = httpServletRequest.getHeader("token");
        Long userId = TokenUtil.getUserId(token);

        try {
            zlFeedbackService.addFeedback(userId, zlFeedbackDTO);
            return new ReturnString(0, "提交反馈成功!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

}
