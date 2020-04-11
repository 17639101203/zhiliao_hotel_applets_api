package com.zhiliao.hotel.controller.news;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.weixinuser.SjWeixinuserController;
import com.zhiliao.hotel.model.SjCbNews;
import com.zhiliao.hotel.service.SjCbNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "咨讯接口")
@RestController
@RequestMapping("/news/")
public class SjCbNewsController {
    private static final Logger logger = LoggerFactory.getLogger(SjWeixinuserController.class);

    @Autowired
    private SjCbNewsService sjCbNewsService;

    @ApiOperation(value = "酒店咨讯展示")
    @PostMapping("findByjiudianId")
    @ResponseBody
    public ReturnString findByjiudianId(@ApiParam(name = "jiudianid", value = "需要查询咨讯的酒店id", required = true) Integer jiudianid){
        try {
            logger.info("酒店ID：" + jiudianid);
            List<SjCbNews> allJiuDianId = sjCbNewsService.findAllJiuDianId(jiudianid);
            return new ReturnString(allJiuDianId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }
    @ApiOperation(value = "酒店咨讯详情展示")
    @PostMapping("findById")
    @ResponseBody
    public ReturnString findById(@ApiParam(name = "id", value = "查询详情咨讯的id", required = true) Integer id){
        try {

            SjCbNews sjCbNews = sjCbNewsService.findById(id);
            return new ReturnString(sjCbNews);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }
}
