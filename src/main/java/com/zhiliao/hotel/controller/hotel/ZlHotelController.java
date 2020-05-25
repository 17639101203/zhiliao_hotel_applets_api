package com.zhiliao.hotel.controller.hotel;

import com.zhiliao.hotel.common.*;
import com.zhiliao.hotel.model.ZlHotelUserHistory;
import com.zhiliao.hotel.service.ZlHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @ApiOperation(value = "陈荣_首页")
    @UserLoginToken
    @GetMapping("getHotelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "query", name = "roomId", dataType = "String", required = false, value = "客房ID"),
    })
    public ReturnString getHotelList(String hotelId, String roomId, HttpServletRequest request) {
        String token = request.getHeader("token");
        return zlHotelService.getById(hotelId, roomId, token);
    }

    @ApiOperation(value = "高翔_获取入住酒店历史")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "int", required = true, value = "每页大小")
    })
    @UserLoginToken
    @GetMapping("getHotelHistoryList/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult> getHotelHistoryList(HttpServletRequest request, @PathVariable Integer pageNo, @PathVariable Integer pageSize){
        String token = request.getHeader("token");
        PageInfoResult hotelHistoryList = zlHotelService.getHotelHistoryList(token, pageNo, pageSize);
        return new ReturnString<>(hotelHistoryList);
    }
}
