package com.zhiliao.hotel.controller.hotelroom;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.service.ZlHotelRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 酒店客房控制层
 * @author chenrong
 * @created date 2020/4/14
 */
@Api(tags = "wifi接口")
@RestController
@RequestMapping("/zl/hotelroom")
public class ZlHotelRoomController {

    @Autowired
    private ZlHotelRoomService zlHotelRoomService;

    @ApiOperation(value = "查询wifi")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "String", required = true, value = "酒店ID")
    })
    @PassToken
    @GetMapping("/findWiFi/{hotelid}")
    public ReturnString findWiFi(@PathVariable Integer hotelid){
        try {
            Map<String,String> wifi = zlHotelRoomService.findWiFi(hotelid);
            return new ReturnString(wifi);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }
}
