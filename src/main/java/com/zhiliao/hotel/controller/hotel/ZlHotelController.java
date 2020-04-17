package com.zhiliao.hotel.controller.hotel;


import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;

import com.zhiliao.hotel.service.ZlHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 酒店控制层
 *
 * @author chenrong
 * @created date 2020/4/10
 */
@Api(tags = "酒店服务调用")
@RestController
@RequestMapping("/zl/hotel")
public class ZlHotelController {

    @Resource
    private ZlHotelService zlHotelService;



    @PassToken
    @GetMapping("getHotelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "roomId", dataType = "String", required = false, value = "客房ID"),
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "凭证"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "String", required = true, value = "类型 1.C端  2.B端"),
    })
    public ReturnString getHotelList(String hotelId, String roomId, String type, String token) throws ParseException {
        ReturnString returnString = zlHotelService.getById(hotelId, roomId, type,token);
        return returnString;
    }

}
