package com.zhiliao.hotel.controller.hotel;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 酒店控制层
 * @author chenrong
 * @created date 2020/4/10
 */
@Api(tags = "酒店服务调用")
@RestController
@RequestMapping("/zl/hotel")
public class ZlHotelController {

    @Resource
    private ZlHotelService zlHotelService;

    @UserLoginToken
    @GetMapping("getHotelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "roomId", dataType = "String", required = false, value = "客房ID"),
    })
    public ReturnString getHotelList(String hotelId, String roomId, String token) {
        return zlHotelService.getById(hotelId, roomId, token);
    }
}
