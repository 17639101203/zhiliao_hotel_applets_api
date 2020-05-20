package com.zhiliao.hotel.controller.myevaluate;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlComment;
import com.zhiliao.hotel.service.myEvaluateService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "我的评价接口_我的_徐向向")
@RestController
@RequestMapping("myEvaluate")
public class myEvaluateController {
    private static final Logger logger = LoggerFactory.getLogger(myEvaluateController.class);

    @Autowired
    private myEvaluateService evaluateService;

    @ApiOperation(value="我的评价订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="token", dataType="String", required=true, value="token"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数"),
    })
    @UserLoginToken
    @PostMapping("evaluateAll")
    public ReturnString listEvaluates(String token, Integer pageNo, Integer pageSize){

        try{
            Long userId= TokenUtil.getUserId(token);
            logger.info("我的评价，用户ID："+userId);
            PageInfoResult result = evaluateService.listEvaluates(userId,pageNo,pageSize);
            return new ReturnString(result);

        }catch(Exception e){
            e.printStackTrace();
            return new ReturnString("获取出错");
        }

    }

    @ApiOperation(value = "评价详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "commentid", dataType = "Integer", required = true, value = "咨讯id")
    })
    @PostMapping("evaluateDetail/{commentid}")
    @ResponseBody
    @PassToken
    public ReturnString evaluateDetail(String token, @PathVariable Integer commentid){
        try {
            logger.info("咨讯id： " +commentid);
            ZlComment comment = evaluateService.evaluateDetail(commentid);
            return new ReturnString(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
