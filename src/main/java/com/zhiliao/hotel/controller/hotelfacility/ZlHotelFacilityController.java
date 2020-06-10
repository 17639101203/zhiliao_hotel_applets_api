package com.zhiliao.hotel.controller.hotelfacility;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.hotelfacility.params.HotelFacilityOrderParam;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
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
import java.util.Map;

@Api(tags = "酒店设施预定接口_首页_徐向向")
@RestController
@RequestMapping("hotelFacility")
public class ZlHotelFacilityController {

    private static final Logger logger = LoggerFactory.getLogger(ZlHotelFacilityController.class);

    @Autowired
    private ZlHotelFacilityService hotelFacilityService;

    @Autowired
    private ZlHotelFacilityOrderService hotelFacilityOrderService;

    @UserLoginToken
    @ApiOperation(value = "酒店设施列表展示")
    @GetMapping("getHotelFacilityList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "int", required = true, value = "酒店ID")
    })
    public ReturnString getHotelFacilityList(Integer hotelId) {
        try {
            List<ZlHotelFacility> facilityList = hotelFacilityService.getHotelFacilityList(hotelId);
            return new ReturnString(facilityList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @UserLoginToken
    @ApiOperation(value = "酒店设施详情")
    @GetMapping("getHotelFacilityDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "facilityID", dataType = "Integer", required = true, value = "酒店ID")
    })
    public ReturnString getHotelFacilityDetail(Integer facilityId) {
        try {
            ZlHotelFacility facilityList = hotelFacilityService.getHotelFacilityDetail(facilityId);
            return new ReturnString(facilityList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @UserLoginToken
    @ApiOperation(value = "酒店设施预定订单提交")
    @PostMapping("addFacilityOrder")
    //@PassToken
    public ReturnString addFacilityOrder(HttpServletRequest request, @RequestBody HotelFacilityOrderParam orderParam) {


        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        //long userId = System.currentTimeMillis();
        ZlHotelFacilityOrder facilityOrder = new ZlHotelFacilityOrder();
        facilityOrder.setUserid(userId);
        facilityOrder.setFacilityid(orderParam.getFacilityId());
        facilityOrder.setHotelid(orderParam.getHotelId());
        facilityOrder.setHotelname(orderParam.getHotelName());
        facilityOrder.setRoomid(orderParam.getRoomid());
        facilityOrder.setRoomnumber(orderParam.getRoomnumber());
        facilityOrder.setFacilityname(orderParam.getFacilityName());
        facilityOrder.setCoverurl(orderParam.getCoverUrl());
        ;
        facilityOrder.setActuallypay(orderParam.getActuallyPay());
        facilityOrder.setUsebegindate(orderParam.getUsebegindate() / 1000);
        facilityOrder.setUseenddate(orderParam.getUseenddate() / 1000);


        try {
            Map<String, Object> map = hotelFacilityService.addFacilityOrder(facilityOrder);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }

    }

    @ApiOperation(value = "酒店设施订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderID", value = "订单ID", required = true)
    })
    @GetMapping("facilityOrderDetail/{orderID}")
    @UserLoginToken
    @ResponseBody
    public ReturnString findOrderDetail(@PathVariable Long orderID) {

        try {
            ZlHotelFacilityOrder zlHotelFacilityOrder = hotelFacilityOrderService.findOrder(orderID);
            return new ReturnString(zlHotelFacilityOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }

    @ApiOperation(value = "取消酒店设施订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderid", value = "订单ID", required = true)
    })
    @GetMapping("cancelRentCarOrder/{orderid}")
    @UserLoginToken
    //@PassToken
    public ReturnString cancelRentCarOrder(@PathVariable Long orderid) {

        try {
            hotelFacilityOrderService.cancelFacilityOrder(orderid);
            return new ReturnString(0, "已取消");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(e.getMessage());
        }
    }

    @ApiOperation(value = "用户删除酒店设施订单_徐向向")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderid", dataType = "Long", required = true, value = "退房订单ID")
    })
    @PostMapping("userDeleteFacilityOrder/{orderid}")
    @UserLoginToken
//    @PassToken
    @ResponseBody
    public ReturnString userDeleteFacilityOrder(@PathVariable("orderid") Long orderid) {
        try {
            hotelFacilityOrderService.userDeleteFacilityOrder(orderid);
            return new ReturnString("用户删除酒店设施成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("用户删除酒店设施失败!");
        }
    }

}
