package com.zhiliao.hotel.controller.cart;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.model.ZlCart;
import com.zhiliao.hotel.service.ZlCartService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xiegege on 2020/4/12.
 */

@Api(tags = "购物车接口")
@RestController
@RequestMapping("cart")
public class ZlCartController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCartController.class);

    private ZlCartService zlCartService;

    @Autowired
    public ZlCartController(ZlCartService zlCartService) {
        this.zlCartService = zlCartService;
    }

    @ApiOperation(value = "购物车添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "goodsId", dataType = "String", required = true, value = "商品id"),
            @ApiImplicitParam(paramType = "path", name = "skuId", dataType = "String", required = true, value = "商品skuId"),
            @ApiImplicitParam(paramType = "path", name = "goodsCount", dataType = "String", required = true, value = "数量")
    })
    @UserLoginToken
    @PostMapping("addCart/{hotelId}/{goodsId}/{skuId}/{goodsCount}")
    public ReturnString addCart(String token, @PathVariable Integer hotelId, @PathVariable Integer goodsId,
                                @PathVariable Integer skuId, @PathVariable Integer goodsCount) {
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("开始请求->参数->酒店id：" + hotelId + "|商品id：" + goodsId + "|商品skuId：" + skuId + "|用户id：" + userId + "|数量：" + goodsCount);
            // 先查询购物车数据是否存在
            ZlCart cart = zlCartService.findCartDoesItExist(userId, hotelId, goodsId, skuId);
            if (cart == null) {
                if (goodsCount == 0) {
                    return new ReturnString("商品数量为0，不执行添加操作");
                }
                // 不存在新增一条购物车数据
                cart = new ZlCart();
                cart.setUserid(userId);
                cart.setHotelid(hotelId);
                cart.setGoodsid(goodsId);
                cart.setSkuid(skuId);
                cart.setGoodscount(goodsCount);
                cart.setCreatedate(1);
                cart.setUpdatedate(1);
                zlCartService.addCart(cart);
            } else {
                // 存在更改数量，如果数量为0，则删除数据
                if (goodsCount == 0) {
                    zlCartService.deleteCartByGoodsCountZero(cart);
                } else {
                    // 更新数量
                    cart.setGoodscount(goodsCount);
                    cart.setUpdatedate(2);
                    zlCartService.updateCartGoodsCount(cart);
                }
            }
            return new ReturnString(0, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("添加出错");
        }
    }

    @ApiOperation(value = "购物车查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产")
    })
    @UserLoginToken
    @GetMapping("findUserCart/{hotelId}/{belongModule}")
    public ReturnString findUserCart(String token, @PathVariable Integer hotelId, @PathVariable Integer belongModule) {
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|用户id：" + userId);
            List<UserCartVo> userCartVoList = zlCartService.findUserCart(hotelId, userId, belongModule);
            return new ReturnString(userCartVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "购物车清空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产")
    })
    @UserLoginToken
    @PostMapping("emptyCart/{hotelId}/{belongModule}")
    public ReturnString emptyCart(String token, @PathVariable Integer hotelId, @PathVariable Integer belongModule) {
        try {
            Long userId = TokenUtil.getUserId(token);
            logger.info("开始请求->参数->酒店id：" + hotelId + "|所属模块：" + belongModule + "|用户id：" + userId);
            zlCartService.emptyCart(hotelId, userId, belongModule);
            return new ReturnString(0, "清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("清空出错");
        }
    }
}