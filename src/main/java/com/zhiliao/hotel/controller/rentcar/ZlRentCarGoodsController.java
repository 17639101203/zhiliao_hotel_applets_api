package com.zhiliao.hotel.controller.rentcar;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.rentcar.params.RentCarOrderParam;
import com.zhiliao.hotel.controller.rentcar.vo.RentCarOrderVO;
import com.zhiliao.hotel.controller.wake.ZlWakeOrderController;
import com.zhiliao.hotel.model.ZlHotel;
import com.zhiliao.hotel.model.ZlHotelroom;
import com.zhiliao.hotel.model.ZlRentCarGoods;
import com.zhiliao.hotel.model.ZlRentCarOrder;
import com.zhiliao.hotel.service.ZlHotelRoomService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.service.ZlRentCarGoodsService;
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
import java.util.Map;

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

    @Autowired
    private ZlHotelRoomService zlHotelRoomService;

    @Autowired
    private ZlHotelService zlHotelService;

    @ApiOperation(value = "租车车型商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "hotelid", value = "酒店ID", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "页码值"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数")
    })
    @GetMapping("carGoodsList/{hotelid}")
    @UserLoginToken
//    @PassToken
    public ReturnString carGoodsList(@PathVariable Integer hotelid, Integer pageNo, Integer pageSize) {

        try {
            PageInfoResult carGoodsList = rentCarGoodsService.carGoodsList(hotelid, pageNo, pageSize);
            return new ReturnString(carGoodsList);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "租车车型商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "goodsid", value = "酒店ID", required = true)
    })
    @GetMapping("rentCarDetail/{goodsid}")
    @UserLoginToken
    public ReturnString rentCarDetail(@PathVariable Integer goodsid) {

        try {
            ZlRentCarGoods rentCarGoods = rentCarGoodsService.rentCarDetail(goodsid);
            return new ReturnString(rentCarGoods);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "提交租车订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "goodsid", value = "车型ID", required = true),
    })
    @PostMapping("addRentCar/{goodsid}")
//    @UserLoginToken
    @PassToken
    public ReturnString addRentCar(HttpServletRequest request, @RequestBody RentCarOrderParam carOrderParam, @PathVariable Integer goodsid) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
//        long userId = System.currentTimeMillis();
        ZlRentCarOrder rentCarOrder = new ZlRentCarOrder();
        rentCarOrder.setHotelid(carOrderParam.getHotelId());
        ZlHotel zlHotel = zlHotelService.getByHotelID(carOrderParam.getHotelId());
        rentCarOrder.setHotelname(zlHotel.getHotelName());
        ZlHotelroom zlHotelroom = zlHotelRoomService.getByRoomNumber(carOrderParam.getRoomNumber(), carOrderParam.getHotelId());
        rentCarOrder.setRoomid(zlHotelroom.getRoomid());
        rentCarOrder.setRoomnumber(carOrderParam.getRoomNumber());
        rentCarOrder.setGoodsname(carOrderParam.getGoodsName());
        rentCarOrder.setCarnumber(carOrderParam.getCarNumber());
        rentCarOrder.setRentbegindate((int) (carOrderParam.getRentBeginDate() / 1000));
        rentCarOrder.setRentenddate((int) (carOrderParam.getRentEndDate() / 1000));
        rentCarOrder.setRentprice(carOrderParam.getRentPrice());
        rentCarOrder.setRenttotalprice(carOrderParam.getRentTotalPrice());
        rentCarOrder.setRemark(carOrderParam.getRemark());
        rentCarOrder.setUsername(carOrderParam.getUserName());
        rentCarOrder.setTel(carOrderParam.getTel());

        try {
            Map<String, Object> map = rentCarGoodsService.addRentCar(userId, rentCarOrder, goodsid);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "租车订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderid", value = "租车订单ID", required = true),
    })
    @GetMapping("rentCarOrderDetail/{orderid}")
    @UserLoginToken
//    @PassToken
    public ReturnString rentCarOrderDetail(@PathVariable long orderid) {

        try {
            RentCarOrderVO rentCarOrderVO = rentCarGoodsService.rentCarOrderDetail(orderid);
            return new ReturnString(rentCarOrderVO);
        } catch (Exception e) {
            return new ReturnString("获取失败");
        }
    }

    @ApiOperation(value = "取消租车订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderid", value = "订单ID", required = true)
    })
    @GetMapping("cancelRentCarOrder/{orderid}")
    @UserLoginToken
//    @PassToken
    public ReturnString cancelRentCarOrder(@PathVariable Long orderid) {

        try {
            rentCarGoodsService.cancelRentCarOrder(orderid);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户删除租车订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderid", value = "订单ID", required = true)
    })
    @GetMapping("dlRentCarOrder/{orderid}")
    @UserLoginToken
//    @PassToken
    public ReturnString dlRentCarOrder(@PathVariable Long orderid) {

        try {
            rentCarGoodsService.dlRentCarOrder(orderid);
            return new ReturnString(0, "删除成功");
        } catch (Exception e) {
            return new ReturnString(e.getMessage());
        }
    }
}
