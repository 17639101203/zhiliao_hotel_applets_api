package com.zhiliao.hotel.controller.eatRent;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.eatRent.vo.ZlDisburdeneatliveVO;
import com.zhiliao.hotel.model.ZlDisburdeneatlive;
import com.zhiliao.hotel.service.ZlDisburdeneatliveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Api(tags = "首页_安心食住接口_姬慧慧")
@RestController
@RequestMapping("eatRent")
public class ZlDisburdeneatliveController {

    @Autowired
    private ZlDisburdeneatliveService disburdeneatliveService;

    @ApiOperation(value = "安心食住_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "int", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "roomID", dataType = "int", required = true, value = "房间ID")
    })
    @UserLoginToken
//    @PassToken
    @PostMapping("findEatRentInfo/{hotelID}/{roomID}")
    public ReturnString findEatRentInfo(@PathVariable("hotelID") Integer hotelID, @PathVariable("roomID") Integer roomID) {
        try {
            ZlDisburdeneatliveVO zlDisburdeneatliveVO = disburdeneatliveService.findEatRentInfo(hotelID, roomID);
            return new ReturnString(zlDisburdeneatliveVO);
        } catch (RuntimeException e) {
            return new ReturnString(e.getMessage());
        }
    }

}
