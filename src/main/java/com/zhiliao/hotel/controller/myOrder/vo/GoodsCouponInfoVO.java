package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 15:31
 **/
@Data
public class GoodsCouponInfoVO {

    /**
     * 用户优惠券绑定唯一id
     */
    private Long couponUserId;

    /**
     * 优惠券ID
     */
    private Integer couponID;

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 1:便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongModule;

}
