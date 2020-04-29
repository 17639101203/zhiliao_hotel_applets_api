package com.zhiliao.hotel.controller.goods;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.model.ZlGoodsCategory;
import com.zhiliao.hotel.service.ZlGoodsCateGoryService;
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
 * Created by xiegege on 2020/4/15.
 */

@Api(tags = "商品接口")
@RestController
@RequestMapping("goods")
public class ZlGoodsController {

    private static final Logger logger = LoggerFactory.getLogger(ZlGoodsController.class);

    // 允许最大的pageSize
    private final static int MAX_PAGE_SIZE = 20;

    private final ZlGoodsService zlGoodsService;

    private final ZlGoodsCateGoryService goodsCateGoryService;

    @Autowired
    public ZlGoodsController(ZlGoodsService zlGoodsService,ZlGoodsCateGoryService goodsCateGoryService) {
        this.zlGoodsService = zlGoodsService;
        this.goodsCateGoryService = goodsCateGoryService;
    }

    @ApiOperation(value = "获取商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产")
    })
    @UserLoginToken
    @GetMapping("findGoodsCategory/{hotelId}/{belongModule}")
    public ReturnString findGoodsCategory(String token, @PathVariable Integer hotelId, @PathVariable Integer belongModule) {
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
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "获取商品分类列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产"),
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "String", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "String", required = true, value = "每页大小"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", dataType = "String", required = true, value = "分类名称")

    })
    @UserLoginToken
    @GetMapping("findGoodsList/{hotelId}/{belongModule}/{pageNo}/{pageSize}")
    public ReturnString findGoodsList(String token, @PathVariable Integer hotelId, @PathVariable Integer belongModule,
                                      @PathVariable Integer pageNo, @PathVariable Integer pageSize, String categoryName) {
        try {
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|页码：" + pageNo + "|每页大小：" + pageSize + "|分类名称：" + categoryName);
            pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
            PageInfoResult goodsList = zlGoodsService.findGoodsList(hotelId, belongModule, pageNo, pageSize, categoryName);
            return new ReturnString(goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "获取商品规格")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "goodsId", dataType = "String", required = true, value = "商品id"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "Integer", required = true, value = "酒店id")
    })
    //@UserLoginToken
    @PassToken
    @GetMapping("findGoodsSkuList/{goodsId}/{hotelId}")
    public ReturnString findGoodsSkuList(String token, @PathVariable Integer goodsId,@PathVariable Integer hotelId) {
        try {
            logger.info("开始请求->参数->商品id：" + goodsId);
            List<Map<String, Object>> goodsSkuList = zlGoodsService.findGoodsSkuList(goodsId,hotelId);
            return new ReturnString(goodsSkuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "获取商品详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "goodsID", dataType = "Integer", required = true, value = "商品id"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "Integer", required = true, value = "酒店id")
    })
    @UserLoginToken
    @GetMapping("findGoodsDetail/{goodsID}/{hotelId}")
    public ReturnString findGoodsDetail(String token, @PathVariable Integer goodsID,@PathVariable Integer hotelId) {
        try {
            GoodsListVo goodsListVo = zlGoodsService.findGoodsDetail(goodsID,hotelId);
            return new ReturnString(goodsListVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }


    @ApiOperation(value = "获取酒店商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "int", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "int", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产")
    })
//    @UserLoginToken
    @PassToken
    @GetMapping("getGoodsCateGory/{hotelId}/{belongModule}")
    public ReturnString getGoodsCateGory(String token,@PathVariable Integer hotelId,@PathVariable Integer belongModule){
        try {
            List<Map<String,String>> categoryList = goodsCateGoryService.getGoodsCateGory(hotelId,belongModule);
            //定义list集合存放商品分类信息
            List<String> list = new ArrayList<>();
            String str = "推荐";
            for (Map<String,String> goodsCateGoryMap : categoryList) {
                //判断是否有推荐
                if (goodsCateGoryMap.get("IsRecommand").equals(str)){
                    list.add(str);
                }
                list.add(goodsCateGoryMap.get("CategoryName"));
            }
            //将推荐放到首位
            if (list.size() != 0 && list.contains(str)){
                int now = list.indexOf(str);
                Collections.swap(list,now,0);
            }
            //去重
            // 有序去重
            Set<String> set = new LinkedHashSet<>(list);
            return new ReturnString(set);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }

    }

}