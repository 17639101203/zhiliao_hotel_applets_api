package com.zhiliao.hotel.controller.news;

import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlNews;
import com.zhiliao.hotel.service.ZlNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "咨讯接口_首页_徐向向")
@RestController
@RequestMapping("news")
public class ZlNewsController {

    private static final Logger logger = LoggerFactory.getLogger(ZlNewsController.class);

    private final ZlNewsService zlNewsService;

    @Autowired
    public ZlNewsController(com.zhiliao.hotel.service.ZlNewsService zlNewsService) {
        this.zlNewsService = zlNewsService;
    }

    @ApiOperation(value = "酒店咨讯展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "int", required = true, value = "酒品id"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码值"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数"),
    })
    @GetMapping("findByHoteId/{hotelID}")
    @ResponseBody
    //@PassToken
    @UserLoginToken
    public ReturnString findByjiudianId(@PathVariable Integer hotelID, Integer pageNo, Integer pageSize) {
        try {
            logger.info("酒店ID：" + hotelID);

            PageInfoResult allNews = zlNewsService.findAllHoteId(hotelID,pageNo, pageSize);

            return new ReturnString(allNews);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "酒店咨讯详情展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "newsid", dataType = "Integer", required = true, value = "咨讯id")
    })
    @GetMapping("findById/{newsid}")
    @ResponseBody
    //@PassToken
    @UserLoginToken
    public ReturnString findById(String token, @PathVariable Integer newsid) {
        try {
            logger.info("咨讯id： " + newsid);
            ZlNews zlNews = zlNewsService.findById(newsid);
            return new ReturnString(zlNews);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
