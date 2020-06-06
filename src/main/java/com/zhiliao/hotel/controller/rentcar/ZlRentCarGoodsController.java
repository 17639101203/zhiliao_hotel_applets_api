package com.zhiliao.hotel.controller.rentcar;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.wake.ZlWakeOrderController;
import com.zhiliao.hotel.model.ZlRentCarGoods;
import com.zhiliao.hotel.service.ZlRentCarGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-06-06 09:22
 **/
@Api(tags = "租车服务接口_首页_徐向向")
@RestController
@RequestMapping("rentCar")
public class ZlRentCarGoodsController {

    private static final Logger logger = LoggerFactory.getLogger(ZlRentCarGoodsController.class);

    @Autowired
    private ZlRentCarGoodsService rentCarGoodsService;

    @ApiOperation(value = "租车车型商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "hotelid", value = "酒店ID", required = true),
            @ApiImplicitParam(paramType="path", name="pageNo", dataType="int", required=true, value="页码值"),
            @ApiImplicitParam(paramType="path", name="pageSize", dataType="int", required=true, value="每页条数")
    })
    @GetMapping("carGoodsList/{hotelid}/{pageNo}/{pageSize}")
    @UserLoginToken
    public ReturnString carGoodsList(@PathVariable Integer hotelid,@PathVariable Integer pageNo,@PathVariable Integer pageSize){

        try {
            PageInfoResult carGoodsList = rentCarGoodsService.carGoodsList(hotelid,pageNo,pageSize);
            return new ReturnString(carGoodsList);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "租车车型商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "goodsid", value = "酒店ID", required = true),
    })
    @GetMapping("rentCarDetail/{goodsid}")
    @UserLoginToken
    public ReturnString rentCarDetail(@PathVariable Integer goodsid){

        try {
            ZlRentCarGoods rentCarGoods = rentCarGoodsService.rentCarDetail(goodsid);
            return new ReturnString(rentCarGoods);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }
}
