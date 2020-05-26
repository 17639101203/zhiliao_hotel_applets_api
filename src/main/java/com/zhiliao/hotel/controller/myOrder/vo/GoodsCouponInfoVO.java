package com.zhiliao.hotel.controller.myOrder.vo;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 15:31
 **/
public class GoodsCouponInfoVO {

    /**
     * 用户优惠券绑定唯一id
     */
    private Integer recID;

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

    public Short getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(Short belongModule) {
        this.belongModule = belongModule;
    }

    public Integer getRecID() {
        return recID;
    }

    public void setRecID(Integer recID) {
        this.recID = recID;
    }

    public Integer getCouponID() {
        return couponID;
    }

    public void setCouponID(Integer couponID) {
        this.couponID = couponID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
