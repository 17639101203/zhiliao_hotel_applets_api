package com.zhiliao.hotel.controller.menu;

import com.alibaba.fastjson.JSON;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.model.ZlBanner;
import com.zhiliao.hotel.service.ZlBannerService;
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

/**
 * @author 邓菡晨
 * @date 2020/5/19 15:42
 */
@Api(tags = "轮播图接口_陈荣")
@RestController
@RequestMapping("banner")
public class ZlBannerController {

    private final ZlBannerService zlBannerService;

    @Autowired
    public ZlBannerController(ZlBannerService zlBannerService) {
        this.zlBannerService = zlBannerService;
    }

    @ApiOperation(value = "查询所属轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "menuID", dataType = "String", required = false, value = "菜单ID"),
    })
    @UserLoginToken
    @GetMapping("findBanner/{hotelID}/{menuID}")
    public ReturnString findBanner(@PathVariable Integer hotelID,@PathVariable Integer menuID) {
        try {
            List<ZlBanner> zlBanners = zlBannerService.findBanner(hotelID,menuID);
            return new ReturnString(JSON.toJSON(zlBanners));
        } catch (Exception e) {

            return new ReturnString("查询失败");
        }
    }
}
