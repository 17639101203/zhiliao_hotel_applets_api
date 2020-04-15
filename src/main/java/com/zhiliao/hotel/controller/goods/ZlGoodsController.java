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

import java.util.*;

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

    @ApiOperation(value = "商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产")
    })
    @UserLoginToken
    @PostMapping("findGoodsCategory/{hotelId}/{belongModule}")
    public ReturnString findGoodsCategory(String token, @PathVariable Integer hotelId, @PathVariable Integer belongModule) {
        try {
            List<Map<String, String>> goodsCategoryDataList = zlGoodsService.findGoodsCategory(hotelId, belongModule);
            String str = "推荐";
            List<String> goodsCategoryList = new ArrayList<>();
            for (Map<String, String> goodsCategoryMap : goodsCategoryDataList) {
                // 判断是否有推荐
                if (goodsCategoryMap.get("IsRecommand").equals(str)) {
                    goodsCategoryList.add(str);
                }
                goodsCategoryList.add(goodsCategoryMap.get("CategoryName"));
            }
            if (goodsCategoryList.size() != 0 && goodsCategoryList.contains(str)) {
                // 把推荐移到第一位
                int now = goodsCategoryList.indexOf(str);
                Collections.swap(goodsCategoryList, now, 0);
            }
            // 有序去重
            Set<String> goodsCategorySet = new LinkedHashSet<>(goodsCategoryList);
            return new ReturnString(goodsCategorySet);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token")
    })
    @UserLoginToken
    @PostMapping("findGoodsList")
    public ReturnString findGoodsList(String token) {
        try {

            return new ReturnString(0, "获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
}