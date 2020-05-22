package com.zhiliao.hotel.controller.wifi;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.controller.wifi.vo.WifiVo;
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

import java.util.List;
import java.util.Map;

/**
 * @author 邓菡晨
 * @date 2020/5/19 15:45
 */
@Api(tags = "首页_wifi接口_陈荣")
@RestController
@RequestMapping("wifi")
public class ZlHotelRoomController {

    private final ZlHotelRoomService zlHotelRoomService;

    @Autowired
    public ZlHotelRoomController(ZlHotelRoomService zlHotelRoomService) {
        this.zlHotelRoomService = zlHotelRoomService;
    }

    @ApiOperation(value = "查询wifi")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "String", required = true, value = "酒店ID")
    })
    @PassToken
    @GetMapping("{hotelID}")
    public ReturnString findWiFi(@PathVariable Integer hotelID){
        try {
            List<WifiVo> wifi = zlHotelRoomService.findWiFi(hotelID);
            if (wifi.size()>0) {
                return new ReturnString(wifi);
            }
            return new ReturnString("该酒店无wifi");
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }
}
