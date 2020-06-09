package com.zhiliao.hotel.controller.eatRent;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import com.zhiliao.hotel.service.ZlDisburdeneatliveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Api(tags = "首页_安心食住接口_林生")
@RestController
@RequestMapping("eatRent")
public class ZlDisburdeneatliveController {

    @Autowired
    private ZlDisburdeneatliveService disburdeneatliveService;

    // TODO: 2020/5/25  @PassToken
    @ApiOperation(value = "安心食住_林生")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelID", dataType = "int", required = true, value = "酒店ID")
    })
     @UserLoginToken
//    @PassToken
    @PostMapping("findEatRentInfo")
    public ReturnString findEatRentInfo(Integer hotelID) {
        try {
            ZlDisburdeneatlive zlDisburdeneatlive = disburdeneatliveService.findEatRentInfo(hotelID);
            return new ReturnString(zlDisburdeneatlive);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

}
