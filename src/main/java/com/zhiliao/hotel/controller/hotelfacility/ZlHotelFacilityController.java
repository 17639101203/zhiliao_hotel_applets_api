package com.zhiliao.hotel.controller.hotelfacility;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.PassToken;
import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.goods.ZlGoodsController;
import com.zhiliao.hotel.controller.hotelfacility.vo.FacilityOrderVo;
import com.zhiliao.hotel.model.ZlHotelFacility;
import com.zhiliao.hotel.model.ZlHotelFacilityOrder;
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

import java.util.List;
import java.util.Map;

@Api(tags = "酒店设施预定接口")
@RestController
@RequestMapping("hotelFacility")
public class ZlHotelFacilityController {

    private static final Logger logger = LoggerFactory.getLogger(ZlHotelFacilityController.class);

    @Autowired
    private ZlHotelFacilityService hotelFacilityService;

    @UserLoginToken
    @ApiOperation(value = "酒店设施列表展示")
    @GetMapping("getHotelFacilityList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "hotelId", dataType = "Integer", required = true, value = "酒店ID")
    })
    public ReturnString getHotelFacilityList(Integer hotelId, String token) {
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
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "query", name = "facilityID", dataType = "Integer", required = true, value = "酒店ID")
    })
    public ReturnString getHotelFacilityDetail(Integer facilityId, String token) {
        try {
            ZlHotelFacility facilityList = hotelFacilityService.getHotelFacilityDetail(facilityId);
            return new ReturnString(facilityList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    //@UserLoginToken
    @ApiOperation(value = "酒店设施预定")
    @PostMapping("addFacilityOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token")

    })
    @PassToken
    public ReturnString addFacilityOrder(String token, @RequestBody ZlHotelFacilityOrder zlHotelFacilityOrder) {
        try {
            Long userId = TokenUtil.getUserId(token);
            zlHotelFacilityOrder.setUserid(userId);
            Map<String, Object> map = hotelFacilityService.addFacilityOrder(zlHotelFacilityOrder);
            return new ReturnString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
}
