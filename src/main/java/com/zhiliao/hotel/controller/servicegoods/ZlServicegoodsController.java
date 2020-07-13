package com.zhiliao.hotel.controller.servicegoods;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.servicegoods.vo.ServicegoodsListVo;
import com.zhiliao.hotel.service.ZlServicegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author xiegege
 * @date 2020/4/23
 */

@Api(tags = "首页_客房服务商品接口_谢辉益")
@RestController
@RequestMapping("servicegoods")
public class ZlServicegoodsController {

    private static final Logger logger = LoggerFactory.getLogger(ZlServicegoodsController.class);

    // 允许最大的pageSize
    private final static int MAX_PAGE_SIZE = 20;

    private final ZlServicegoodsService zlServicegoodsService;

    @Autowired
    public ZlServicegoodsController(ZlServicegoodsService zlServicegoodsService) {
        this.zlServicegoodsService = zlServicegoodsService;
    }

    @ApiOperation(value = "获取客房服务商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务")
    })
    @UserLoginToken
    @GetMapping("findServicegoodsCategory/{hotelId}/{belongModule}")
    public ReturnString<List<String>> findServicegoodsCategory(@PathVariable Integer hotelId, @PathVariable Integer belongModule) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule);
            List<String> servicegoodsCategoryList = zlServicegoodsService.findServicegoodsCategory(hotelId, belongModule);
            return new ReturnString(servicegoodsCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取客房服务商品分类出错!");
        }
    }

    @ApiOperation(value = "获取客房服务商品列表数据（按分类名称查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", dataType = "String", required = true, value = "分类名称（all代表全部）")

    })
    @UserLoginToken
    @GetMapping("findServicegoodsList/{hotelId}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult<ServicegoodsListVo>> findServicegoodsList(@PathVariable Integer hotelId, @PathVariable Integer belongModule,
                                                                                 @PathVariable Integer pageNo, @PathVariable Integer pageSize, String categoryName) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|页码：" + pageNo + "|每页大小：" + pageSize + "|分类名称：" + categoryName);
            pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
            PageInfoResult servicegoodsList = zlServicegoodsService.findServicegoodsList(hotelId, belongModule, pageNo, pageSize, categoryName, null);
            return new ReturnString(servicegoodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取客房服务商品列表出错!");
        }
    }

    @ApiOperation(value = "获取客房服务商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "goodsId", dataType = "String", required = true, value = "客房服务商品id")
    })
    @UserLoginToken
    @GetMapping("findServicegoodsDetail/{goodsId}")
    public ReturnString<ServicegoodsListVo> findServicegoodsDetail(@PathVariable Integer goodsId) {
        try {
            logger.info("开始请求->参数->客房服务商品id：" + goodsId);
            ServicegoodsListVo servicegoodsListVo = zlServicegoodsService.findServicegoodsDetail(goodsId);
            return new ReturnString(servicegoodsListVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取客房服务商品详情出错!");
        }
    }

    @ApiOperation(value = "客房服务商品搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务"),
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", required = true, value = "搜索关键词"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小")
    })
    @UserLoginToken
    @GetMapping("searchServicegoods/{hotelId}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult<ServicegoodsListVo>> searchServicegoods(@PathVariable Integer hotelId, @PathVariable Integer belongModule,
                                                                               String keyword, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|页码：" + pageNo + "|每页大小：" + pageSize + "|搜索关键词：" + keyword);
            pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
            PageInfoResult searchServicegoodsList = zlServicegoodsService.findServicegoodsList(hotelId, belongModule, pageNo, pageSize, null, keyword);
            return new ReturnString(searchServicegoodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(-1, "获取客房服务商品搜索数据出错!");
        }
    }

    @ApiOperation(value = "查询客房服务全部商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小")
    })
    @UserLoginToken
    @GetMapping("searchAllServicegoods/{hotelId}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult<ServicegoodsListVo>> searchAllServicegoods(@PathVariable Integer hotelId,
                                                                                  @PathVariable Integer pageNo,
                                                                                  @PathVariable Integer pageSize) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|页码：" + pageNo + "|每页大小：" + pageSize);
            pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
            PageInfoResult searchServicegoodsList = zlServicegoodsService.searchAllServicegoods(hotelId, pageNo, pageSize);
            return new ReturnString(searchServicegoodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(-1, "获取客房服务商品搜索数据出错!");
        }
    }

}