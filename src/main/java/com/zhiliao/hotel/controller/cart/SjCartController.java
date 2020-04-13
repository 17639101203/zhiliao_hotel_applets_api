package com.zhiliao.hotel.controller.cart;

import com.alibaba.fastjson.JSONArray;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.utils.RedisCommonUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/12.
 */

@Api(tags = "购物车接口")
@RestController
@RequestMapping("cart")
public class SjCartController {

    private static final Logger logger = LoggerFactory.getLogger(SjCartController.class);

    private RedisCommonUtil redisCommonUtil;
    private TokenUtil tokenUtil;

    @Autowired
    public SjCartController(RedisCommonUtil redisCommonUtil, TokenUtil tokenUtil) {
        this.redisCommonUtil = redisCommonUtil;
        this.tokenUtil = tokenUtil;
    }

    @ApiOperation(value = "购物车添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "jiuDianId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "cartType", dataType = "String", required = true, value = "购物车类型：0客房服务，1便利店，2餐饮服务，3情趣用品，4土特产")
    })
    @UserLoginToken
    @PostMapping("addCart/{jiuDianId}/{cartType}")
    public ReturnString addCart(String token, @PathVariable Integer jiuDianId, @PathVariable Integer cartType, AddCartParam addCartParam) {
        try {
            Integer weixinuserId = tokenUtil.getWeixinuserId(token);
            logger.info("开始请求->参数->酒店id：" + jiuDianId + "|购物车类型：" + cartType + "|用户id：" + weixinuserId + "|参数：" + addCartParam.toString());
            // 先从redis中获取数据，判断购物车中是否有数据  cart购物车类型-酒店id-用户id
            Map<String, String> dataMap = (Map<String, String>) redisCommonUtil.getHashCache("cart" + cartType + "-" + jiuDianId + "-" + weixinuserId);
            String cartJson = JSONArray.toJSON(addCartParam).toString();
            // 操作成功布尔值
            boolean flag = false;
            // 商品id
            String goodsId = String.valueOf(addCartParam.getGoodsId());
            // 删除购物车
            redisCommonUtil.deleteCache("cart" + cartType + "-" + jiuDianId + "-" + weixinuserId);
            if (dataMap == null || dataMap.size() == 0) {
                if (addCartParam.getNum() != 0) {
                    // 新增数据到redis
                    dataMap = new HashMap<>();
                    dataMap.put(goodsId, cartJson);
                    flag = redisCommonUtil.setHashCache("cart" + cartType + "-" + jiuDianId + "-" + weixinuserId, dataMap, 60 * 60 * 24 * 3);
                } else {
                    return new ReturnString(0, "商品数量为0，不执行添加操作");
                }
            } else {
                // 取出数据判断商品是否存在，更改数量
                if (dataMap.containsKey(goodsId)) {
                    // 传过来的数量为0则删除这条数据
                    if (addCartParam.getNum() == 0) {
                        dataMap.remove(goodsId);
                    } else {
                        // 购物车中有记录，更新购物车这条商品的数据
                        dataMap.put(goodsId, cartJson);
                    }
                } else {
                    // 购物车中无记录，新增一条
                    dataMap.put(goodsId, cartJson);
                }
                if (dataMap.size() > 0) {
                    // dataMap的size要大于0，才执行添加。为0的时候说明购物车已经清空
                    flag = redisCommonUtil.setHashCache("cart" + cartType + "-" + jiuDianId + "-" + weixinuserId, dataMap, 60 * 60 * 24 * 3);
                } else {
                    flag = true;
                }
            }
            if (flag) {
                return new ReturnString(0, "添加成功");
            }
            return new ReturnString("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("添加失败");
        }
    }
}