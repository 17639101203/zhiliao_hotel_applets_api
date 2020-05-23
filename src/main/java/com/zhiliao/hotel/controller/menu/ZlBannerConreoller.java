package com.zhiliao.hotel.controller.menu;

import com.alibaba.fastjson.JSON;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
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
public class ZlBannerConreoller {

    private final ZlBannerService zlBannerService;
    private List<ZlBanner> zlBanners;

    @Autowired
    public ZlBannerConreoller(ZlBannerService zlBannerService) {
        this.zlBannerService = zlBannerService;
    }

    @ApiOperation(value = "查询所属轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelID", dataType = "String", required = true, value = "酒店ID"),
            @ApiImplicitParam(paramType = "path", name = "menuID", dataType = "String", required = false, value = "菜单ID"),
    })
    @PassToken
    @GetMapping("findBanner/{hotelID}/{menuID}")
    public ReturnString findBanner(String token, @PathVariable Integer hotelID,@PathVariable Integer menuID) {
        try {
            List<ZlBanner> zlBanners = zlBannerService.findBanner(hotelID,menuID);
            return new ReturnString(JSON.toJSON(zlBanners));
        } catch (Exception e) {

            return new ReturnString("查询失败");
        }
    }
}
