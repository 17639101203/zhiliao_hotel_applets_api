package com.zhiliao.hotel.controller.servicegoods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiegege
 * @date 2020/4/23
 */

@ApiModel("客房服务商品vo")
public class ServicegoodsListVo {

    @ApiModelProperty(value = "客房服务商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "客房服务商品名称")
    private String goodsName;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "客房服务商品封面图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "客房服务商品详情")
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}