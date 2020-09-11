package com.zhiliao.hotel.controller.coupon;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.coupon.dto.CouponIdListDTO;
import com.zhiliao.hotel.controller.coupon.dto.CouponReceivedDTO;
import com.zhiliao.hotel.controller.coupon.vo.CouponReceivedVO;
import com.zhiliao.hotel.model.ZlCoupon;
import com.zhiliao.hotel.service.ZlCouponService;
import com.zhiliao.hotel.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 09:58
 **/
@Api(tags = "优惠卷接口_姬慧慧")
@RequestMapping("coupon")
@RestController
public class ZlCouponController {

    private static final Logger logger = LoggerFactory.getLogger(ZlCouponController.class);

    @Autowired
    private ZlCouponService couponService;

    @ApiOperation(value = "展示用户待领取的优惠卷_姬慧慧")
    @UserLoginToken
    @GetMapping("couponUnclaimed")
    public ReturnString couponUnclaimed(HttpServletRequest request) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            Map<String, List<ZlCoupon>> zlCouponListMap = couponService.couponUnclaimed(userId);
            return new ReturnString(zlCouponListMap);
        } catch (RuntimeException e) {
            return new ReturnString(e.getMessage());
        }

    }

    @ApiOperation(value = "用户点击领取优惠卷_姬慧慧")
    @UserLoginToken
    @PostMapping("couponReceive")
    public ReturnString couponReceive(HttpServletRequest request, @Validated @RequestBody List<CouponIdListDTO> couponIdListDTOList) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            couponService.couponReceive(userId, couponIdListDTOList);
            return new ReturnString(0, "优惠券领取成功!");
        } catch (RuntimeException e) {
            return new ReturnString(e.getMessage());
        }

    }

    @ApiOperation(value = "展示用户已领取的优惠卷_姬慧慧")
    @UserLoginToken
    @PostMapping("couponReceived")
    public ReturnString couponReceived(HttpServletRequest request,
                                       @RequestBody CouponReceivedDTO couponReceivedDTO) {

        try {
            String token = request.getHeader("token");
            Long userId = TokenUtil.getUserId(token);
            Map<String, List<CouponReceivedVO>> zlCouponListMap = couponService.couponReceived(userId, couponReceivedDTO);
            return new ReturnString(zlCouponListMap);
        } catch (RuntimeException e) {
            return new ReturnString(e.getMessage());
        }

    }

}
