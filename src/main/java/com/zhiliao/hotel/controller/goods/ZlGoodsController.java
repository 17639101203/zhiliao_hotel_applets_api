package com.zhiliao.hotel.controller.goods;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.service.ZlGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiegege on 2020/4/15.
 */

@Api(tags = "商品接口")
@RestController
@RequestMapping("goods")
public class ZlGoodsController {

    private ZlGoodsService zlGoodsService;

    @Autowired
    public ZlGoodsController(ZlGoodsService zlGoodsService) {
        this.zlGoodsService = zlGoodsService;
    }

    @ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token")
    })
    @UserLoginToken
    @PostMapping("findGoodsList")
    public ReturnString findGoodsList(@PathVariable String token) {
        try {

            return new ReturnString(0, "获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
}