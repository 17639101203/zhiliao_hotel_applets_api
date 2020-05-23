package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 购物车表
 *
 * @author xiehuiyi
 * @date 2020-04-14
 */
public class ZlCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    private Long cartid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * skuID
     */
    private Integer skuid;

    /**
     * 酒店商品ID
     */
    private Integer hotelgoodsid;

    /**
     * 商品数量
     */
    private Integer goodscount;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    public Long getCartid() {
        return cartid;
    }

    public void setCartid(Long cartid) {
        this.cartid = cartid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public Integer getSkuid() {
        return skuid;
    }

    public void setSkuid(Integer skuid) {
        this.skuid = skuid;
    }

    public Integer getHotelgoodsid() {
        return hotelgoodsid;
    }

    public void setHotelgoodsid(Integer hotelgoodsid) {
        this.hotelgoodsid = hotelgoodsid;
    }

    public Integer getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(Integer goodscount) {
        this.goodscount = goodscount;
    }

    public Short getBelongmodule() {
        return belongmodule;
    }

    public void setBelongmodule(Short belongmodule) {
        this.belongmodule = belongmodule;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public Integer getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }
}