package com.zhiliao.hotel.controller.hotelfacility;

import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
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
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "Integer", required = true, value = "酒店ID")
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
    @ApiImplicitParams({
    })
    @PostMapping("addFacilityOrder")
    //@PassToken
    public ReturnString addFacilityOrder(HttpServletRequest request, @RequestBody ZlHotelFacilityOrder zlHotelFacilityOrder) {


            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            //long userId = System.currentTimeMillis();
            zlHotelFacilityOrder.setUserid(userId);
            ReturnString returnString = hotelFacilityService.addFacilityOrder(zlHotelFacilityOrder);
            return new ReturnString(returnString);

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
            ZlHotelFacilityOrder zlHotelFacilityOrder = hotelFacilityOrderService.findOrder( orderID);
            return new ReturnString(zlHotelFacilityOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }



}
