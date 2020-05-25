package com.zhiliao.hotel.controller.menu;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlMenuvisitlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * '菜单模块访问记录表（埋点）
 *
 * @author 邓菡晨
 * @Package com.zhiliao.hotel.controller.menu
 * @Classname ZlMenuvisitlogController
 * @date 2020/4/16 9:30
 */
@Api(tags = "菜单点击记录接口")
@RestController
@RequestMapping("log")
public class ZlMenuvisitlogController {

    @Autowired
    private ZlMenuvisitlogService zlMenuvisitlogService;

    @ApiOperation(value = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "menuId", dataType = "String", required = true, value = "菜单ID"),
            @ApiImplicitParam(paramType = "path", name = "userId", dataType = "String", required = true, value = "用户ID")
    })
    @UserLoginToken
    @PostMapping("add/{menuId}/{userId}")
    public ReturnString findBanner(@PathVariable Integer menuId, @PathVariable Integer userId) {
        try {
            zlMenuvisitlogService.add(menuId, userId);
            return new ReturnString(0, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("添加失败 " + e);
        }
    }
}