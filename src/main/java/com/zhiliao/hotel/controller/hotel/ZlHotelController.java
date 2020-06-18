package com.zhiliao.hotel.controller.hotel;

import com.zhiliao.hotel.common.*;
import com.zhiliao.hotel.model.ZlHotelRoomQrcode;
import com.zhiliao.hotel.service.ZlHotelRoomQrcodeService;
import com.zhiliao.hotel.service.ZlHotelService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 酒店控制层
 *
 * @author chenrong
 * @created date 2020/4/10
 */
@Api(tags = "酒店信息(首页)_陈荣_高翔")
@RestController
@RequestMapping("/zl/hotel")
public class ZlHotelController {

    @Resource
    private ZlHotelService zlHotelService;

    @Resource
    private ZlHotelRoomQrcodeService zlHotelRoomQrcodeService;

    @ApiOperation(value = "陈荣_首页")
    @UserLoginToken
//    @PassToken
    @GetMapping("getHotelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "codeId", dataType = "String", required = true, value = "二维码ID"),
    })
    public ReturnString getHotelList(String codeId, HttpServletRequest request) {
        ZlHotelRoomQrcode roomQrcodeId = zlHotelRoomQrcodeService.getRoomQrcodeId(codeId);
        if (roomQrcodeId != null) {
            String token = request.getHeader("token");
            return zlHotelService.getById(roomQrcodeId.getHotelID(), String.valueOf(roomQrcodeId.getRoomID()), token);
        }
        return new ReturnString("二维码不存在,请联系管理员");
    }

    @ApiOperation(value = "高翔_获取入住酒店历史")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNo", dataType = "int", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "int", required = true, value = "每页大小")
    })
    @UserLoginToken
    @GetMapping("getHotelHistoryList/{pageNo}/{pageSize}")
    public ReturnString<PageInfoResult> getHotelHistoryList(HttpServletRequest request, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        String token = request.getHeader("token");
        try {
            PageInfoResult hotelHistoryList = zlHotelService.getHotelHistoryList(token, pageNo, pageSize);
            return new ReturnString<>(hotelHistoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString<>("获取失败!");
        }
    }

    @ApiOperation(value = "姬慧慧_用户删除入住酒店历史")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "recId", dataType = "Long", required = true, value = "入住酒店历史自增id")
    })
    @UserLoginToken
    @GetMapping("getHotelHistoryList/{recId}")
    public ReturnString userDeleteHotelHistory(@PathVariable("recId") Long recId) {
        try {
            zlHotelService.userDeleteHotelHistory(recId);
            return new ReturnString(0, "已删除!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString(-1, "删除失败!");
        }
    }

}
