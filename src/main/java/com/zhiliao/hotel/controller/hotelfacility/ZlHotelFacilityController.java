package com.zhiliao.hotel.controller.hotelfacility;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.hotelfacility.params.HotelFacilityOrderParam;
import com.zhiliao.hotel.controller.hotelfacility.vo.HotelFacilityOrderParamVO;
import com.zhiliao.hotel.controller.myAppointment.dto.HotelFacilityOrderParamDTO;
import com.zhiliao.hotel.mapper.ZlHotelMapper;
import com.zhiliao.hotel.mapper.ZlHotelRoomMapper;
import com.zhiliao.hotel.mapper.ZlWxuserdetailMapper;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlHotelFacilityOrderService;
import com.zhiliao.hotel.service.ZlHotelFacilityService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private ZlHotelRoomMapper zlHotelRoomMapper;

    @Autowired
    private ZlWxuserdetailMapper zlWxuserdetailMapper;

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
            @ApiImplicitParam(paramType = "query", name = "facilityId", dataType = "int", required = true, value = "设施ID")
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

        ZlHotelroom zlHotelroom = zlHotelRoomMapper.getByHotelIDAndRoomNumber(orderParam.getRoomnumber(), orderParam.getHotelId());
        if (zlHotelroom == null) {
            throw new BizException("您的码是前台码，不提供该服务");
        }
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);

        ZlWxuserdetail zlWxuserdetail = new ZlWxuserdetail();
        zlWxuserdetail.setUserid(userId);
        zlWxuserdetail = zlWxuserdetailMapper.selectOne(zlWxuserdetail);

        ZlHotelFacilityOrder facilityOrder = new ZlHotelFacilityOrder();
        facilityOrder.setUserid(userId);
        facilityOrder.setUsername(zlWxuserdetail.getRealname());
        if (StringUtils.isNoneBlank(zlWxuserdetail.getTel())) {
            facilityOrder.setTel(zlWxuserdetail.getTel());
        }
        facilityOrder.setFacilityid(orderParam.getFacilityId());
        facilityOrder.setHotelid(orderParam.getHotelId());
        facilityOrder.setHotelname(orderParam.getHotelName());
        facilityOrder.setRoomid(zlHotelroom.getRoomid());
        facilityOrder.setRoomnumber(orderParam.getRoomnumber());
        facilityOrder.setFacilityname(orderParam.getFacilityName());
        facilityOrder.setCoverurl(orderParam.getCoverUrl());
        facilityOrder.setTotalprice(orderParam.getTotalPrice());
        facilityOrder.setRemark(orderParam.getRemark());
        facilityOrder.setUsebegindate((int) (orderParam.getUsebegindate() / 1000));
        facilityOrder.setUseenddate((int) (orderParam.getUseenddate() / 1000));
        facilityOrder.setTel(orderParam.getTel());
        facilityOrder.setUsername(orderParam.getUserName());

        try {
            Map<String, Object> map = hotelFacilityService.addFacilityOrder(facilityOrder);
            return new ReturnString(map);
        } catch (RuntimeException e) {
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
            HotelFacilityOrderParamDTO hotelFacilityOrderParamDTO = hotelFacilityOrderService.findOrder(orderID);
            return new ReturnString(hotelFacilityOrderParamDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("查询失败");
        }
    }

    @ApiOperation(value = "取消酒店设施订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderid", value = "订单ID", required = true)
    })
    @GetMapping("cancelFacilityOrder/{orderid}")
    @UserLoginToken
    //@PassToken
    public ReturnString cancelFacilityOrder(@PathVariable Long orderid) {

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
