package com.zhiliao.hotel.controller.menu;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlXcxMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "酒店超市菜单_陈荣")
@RestController
@RequestMapping("/xcx/menu")
public class ZlXcxMenuController {

    @Autowired
    private ZlXcxMenuService zlXcxMenuService;

    @GetMapping("getMenuList")
    @UserLoginToken
    @ApiOperation(value = "酒店超市菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
    })
    public ReturnString getMenuList(String hotelId) {
        return new ReturnString(zlXcxMenuService.getMenuList(hotelId,-999));
    }
}
