package com.zhiliao.hotel.controller.hotel;

import com.zhiliao.hotel.common.NoLoginRequiredToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 酒店控制层
 * @author chenrong
 * @created date 2020/4/10
 */
@Api(tags = "酒店信息(首页)_陈荣_高翔")
@RestController
@RequestMapping("/zl/hotel")
public class ZlHotelController {

    @Resource
    private ZlHotelService zlHotelService;

    @NoLoginRequiredToken
    @GetMapping("getHotelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "roomId", dataType = "String", required = false, value = "客房ID"),
    })
    public ReturnString getHotelList(String hotelId, String roomId, String token) {
        return zlHotelService.getById(hotelId, roomId, token);
    }

    @ApiOperation(value = "高翔_获取入住酒店历史")
    @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token")
    @UserLoginToken
    @GetMapping("getHotelHistoryList")
    public ReturnString getHotelHistoryList(String token){
        return zlHotelService.getHotelHistoryList(token);
    }
}
