package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.coupon.dto.CouponIdListDTO;
import com.zhiliao.hotel.controller.coupon.dto.CouponReceivedDTO;
import com.zhiliao.hotel.controller.coupon.vo.CouponReceivedVO;
import com.zhiliao.hotel.controller.coupon.vo.ZlCouponAllVO;
import com.zhiliao.hotel.model.ZlCoupon;

import java.util.List;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-20 09:58
 **/
public interface ZlCouponService {

    Map<String, List<ZlCoupon>> couponUnclaimed(Long userId);

    void couponReceive(Long userId, List<CouponIdListDTO> couponIdListDTOList);

    void autoCouponUserStatus(String couponUserId);

    Map<String, List<CouponReceivedVO>> couponReceived(Long userId, CouponReceivedDTO couponReceivedDTO);
}
