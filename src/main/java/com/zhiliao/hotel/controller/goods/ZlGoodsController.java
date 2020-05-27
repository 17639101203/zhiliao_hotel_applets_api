package com.zhiliao.hotel.controller.goods;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.goods.vo.EsGoods;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuListVo;
import com.zhiliao.hotel.service.ZlGoodsService;
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
 * @date 2020/4/15
 */

@Api(tags = "首页_酒店超市商品接口_谢辉益_姬慧慧")
@RestController
@RequestMapping("goods")
public class ZlGoodsController {

    private static final Logger logger = LoggerFactory.getLogger(ZlGoodsController.class);

    // 允许最大的pageSize
    private final static int MAX_PAGE_SIZE = 20;

    private final ZlGoodsService zlGoodsService;

    @Autowired
    public ZlGoodsController(ZlGoodsService zlGoodsService) {
        this.zlGoodsService = zlGoodsService;
    }

    @ApiOperation(value = "获取商品分类_谢辉益")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产")
    })
    @UserLoginToken
    @GetMapping("findGoodsCategory/{hotelId}/{belongModule}")
    public ReturnString<Set<String>> findGoodsCategory(@PathVariable Integer hotelId, @PathVariable Integer belongModule) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule);
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
            return new ReturnString("获取商品分类出错!");
        }
    }

    @ApiOperation(value = "获取商品列表数据（按分类名称查询）_谢辉益")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", dataType = "String", required = true, value = "分类名称（all代表全部）")
    })
    @UserLoginToken
    @GetMapping("findGoodsList/{hotelId}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult<GoodsListVo>> findGoodsList(@PathVariable Integer hotelId, @PathVariable Integer belongModule,
                                                                   @PathVariable Integer pageNo, @PathVariable Integer pageSize, String categoryName) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|页码：" + pageNo + "|每页大小：" + pageSize + "|分类名称：" + categoryName);
            pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
            PageInfoResult goodsList = zlGoodsService.findGoodsList(hotelId, belongModule, pageNo, pageSize, categoryName);
            return new ReturnString(goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取商品列表数据出错!");
        }
    }

    @ApiOperation(value = "获取商品规格_谢辉益")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "goodsId", dataType = "String", required = true, value = "商品id")
    })
    @UserLoginToken
    @GetMapping("findGoodsSkuList/{hotelId}/{goodsId}")
    public ReturnString findGoodsSkuList(@PathVariable Integer hotelId, @PathVariable Integer goodsId) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|商品id：" + goodsId);
            List<GoodsSkuListVo> goodsSkuList = zlGoodsService.findGoodsSkuList(hotelId, goodsId);
            List<String> propertyNameList = new ArrayList<>();
            for (GoodsSkuListVo goodsSkuListVo : goodsSkuList) {
                propertyNameList.add(goodsSkuListVo.getPropertyName());
            }
            Map<String, Object> dataMap = new HashMap<>(2);
            dataMap.put("propertyNameList", propertyNameList);
            dataMap.put("goodsSkuList", goodsSkuList);
            return new ReturnString(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取商品规格出错!");
        }
    }

    @ApiOperation(value = "获取商品详情数据_谢辉益")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "goodsId", dataType = "String", required = true, value = "商品id")
    })
    @UserLoginToken
    @GetMapping("findGoodsDetail/{goodsId}")
    public ReturnString<GoodsListVo> findGoodsDetail(@PathVariable Integer goodsId) {
        try {
            logger.info("开始请求->参数->商品id：" + goodsId);
            GoodsListVo goodsListVo = zlGoodsService.findGoodsDetail(goodsId);
            return new ReturnString(goodsListVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取商品详情数据出错!");
        }
    }

    @ApiOperation(value = "酒店超市_商品搜索_姬慧慧")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "int", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "selectParam", dataType = "String", required = true, value = "商品名称参数"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "int", required = true, value = "所属模块 0:首页搜索 1:便利店;2餐饮服务;3情趣用品;4土特产"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小")
    })
//    @UserLoginToken
    @PassToken
    @GetMapping("searchGoods/{hotelId}/{selectParam}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString<List<EsGoods>> searchGoods(@PathVariable Integer hotelId,
                                                   @PathVariable String selectParam,
                                                   @PathVariable Integer belongModule,
                                                   @PathVariable Integer pageNo,
                                                   @PathVariable Integer pageSize) {
        try {
            List<EsGoods> esGoodsList = zlGoodsService.searchGoods(hotelId, selectParam, belongModule, pageNo, pageSize);
            return new ReturnString<>(esGoodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("获取出错");
        }
    }
}