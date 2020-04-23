package com.zhiliao.hotel.controller.servicegoods.vo;

/**
 * Created by xiegege on 2020/4/23.
 */

public class ServicegoodsListVo {

    /**
     * 客房服务商品id
     */
    private Integer goodsId;

    /**
     * 客房服务商品名称
     */
    private String goodsName;

    /**
     * 客房服务商品封面图片
     */
    private String coverImgUrl;

    /**
     * 客房服务商品详情
     */
    private String content;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
