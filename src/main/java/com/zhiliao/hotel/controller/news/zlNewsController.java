package com.zhiliao.hotel.controller.news;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.wxuser.ZlWxuserController;
import com.zhiliao.hotel.model.zlNews;
import com.zhiliao.hotel.service.zlNewsService;
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
public class zlNewsController {
    private static final Logger logger = LoggerFactory.getLogger(zlNewsController.class);

    @Autowired
    private zlNewsService zlNewsService;

    @ApiOperation(value = "酒店咨讯展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "Integer", required = true, value = "酒品id")
    })
    @PostMapping("findByjiudianId/{hotelID}")
    @ResponseBody
    @PassToken
    public ReturnString findByjiudianId(String token, @PathVariable Integer hotelID){
        try {
            logger.info("酒店ID：" + hotelID);
            List<zlNews> allJiuDianId;
            Integer type = 1;
            Integer status = 1;
            if (hotelID == 0){
                allJiuDianId = zlNewsService.findAllJiuDianId(hotelID,type,status);
            }else {
                type = 2;
                allJiuDianId = zlNewsService.findAllJiuDianId(hotelID, type,status);
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
            @ApiImplicitParam(paramType = "path", name = "id", dataType = "Integer", required = true, value = "咨讯id")
    })
    @PostMapping("findById/{newsid}")
    @ResponseBody
    @PassToken
    public ReturnString findById(String token, @PathVariable Integer newsid){
        try {
            logger.info("咨讯id： " +newsid);
            zlNews zlNews = zlNewsService.findById(newsid);
            return new ReturnString(zlNews);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取失败");
        }
    }
}
