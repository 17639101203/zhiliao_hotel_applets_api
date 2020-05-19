package com.zhiliao.hotel.controller.news;

import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
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

@Api(tags = "咨讯接口")
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
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Integer", required = true, value = "酒品id"),
            @ApiImplicitParam(paramType="query", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="query", name="pageSize", dataType="int", required=true, value="每页条数"),
    })
    @PostMapping("findByjiudianId/{hotelID}")
    @ResponseBody
    @PassToken
    public ReturnString findByjiudianId(String token, @PathVariable Integer hotelID,Integer pageNo,Integer pageSize){
        try {
            logger.info("酒店ID：" + hotelID);
            PageInfoResult allJiuDianId;
            PageInfo pageInfo;
            Integer type = 1;
            Integer status = 1;
            if (hotelID == 0){
                allJiuDianId = zlNewsService.findAllJiuDianId(hotelID,type,status,pageNo,pageSize);

            }else {
                type = 2;
                allJiuDianId = zlNewsService.findAllJiuDianId(hotelID, type,status,pageNo,pageSize);

            }
            return new ReturnString(allJiuDianId);
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
    @PostMapping("findById/{newsid}")
    @ResponseBody
    @PassToken
    public ReturnString findById(String token, @PathVariable Integer newsid){
        try {
            logger.info("咨讯id： " +newsid);
            ZlNews zlNews = zlNewsService.findById(newsid);
            return new ReturnString(zlNews);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
