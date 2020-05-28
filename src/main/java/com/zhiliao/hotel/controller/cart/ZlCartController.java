package com.zhiliao.hotel.controller.cart;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.cart.params.AddCartParam;
import com.zhiliao.hotel.controller.cart.vo.UserCartVo;
import com.zhiliao.hotel.service.ZlCartService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiegege on 2020/4/12.
 */

@Api(tags = "悬浮图标_购物车接口_谢辉益")
@RestController
@RequestMapping("cart")
public class ZlCartController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCartController.class);

    private final ZlCartService zlCartService;

    @Autowired
    public ZlCartController(ZlCartService zlCartService) {
        this.zlCartService = zlCartService;
    }

    @ApiOperation(value = "用户购物车添加")
    @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id")
    @UserLoginToken
    @PostMapping("addCart/{hotelId}")
    public ReturnString addCart(@PathVariable Integer hotelId, @RequestBody List<AddCartParam> addCartParams, HttpServletRequest request) {
        try {
            if (addCartParams.size() > 0) {
                String token = request.getHeader("token");
                Long userId = TokenUtil.getUserId(token);
                logger.info("开始请求->参数->酒店id：" + hotelId + "|用户id：" + userId + "|购物车长度：" + addCartParams.size());
                // 添加新的购物车数据
                Integer date = DateUtils.javaToPhpNowDateTime();
                zlCartService.addUserCartBatch(hotelId, userId, addCartParams, date);
                return new ReturnString(0, "购物车添加成功!");
            }
            return new ReturnString("传入购物车长度为0，不执行操作!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("购物车添加出错!");
        }
    }

    @ApiOperation(value = "用户购物车查询")
    @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id")
    @UserLoginToken
    @GetMapping("findUserCart/{hotelId}")
    public ReturnString<UserCartVo> findUserCart(@PathVariable Integer hotelId, HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("开始请求->参数->酒店id：" + hotelId + "|用户id：" + userId);
            List<UserCartVo> userCartVoList = zlCartService.findUserCart(hotelId, userId);
            return new ReturnString(userCartVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "用户购物车全部清空/单模块清空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "hotelId", dataType = "String", required = true, value = "酒店id"),
            @ApiImplicitParam(paramType = "path", name = "belongModule", dataType = "String", required = true, value = "所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产（0代表清空所有）")
    })
    @UserLoginToken
    @PostMapping("emptyUserCart/{hotelId}/{belongModule}")
    public ReturnString emptyUserCart(@PathVariable Integer hotelId, @PathVariable Integer belongModule, HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            logger.info("开始请求->参数->酒店id：" + hotelId + "|用户id：" + userId + "|清空模块：" + belongModule);
            zlCartService.emptyUserCart(hotelId, userId, belongModule);
            return new ReturnString(0, "购物车清空成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("购物车清空出错!");
        }
    }
}