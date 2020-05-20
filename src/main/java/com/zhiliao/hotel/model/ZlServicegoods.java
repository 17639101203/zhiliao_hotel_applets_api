package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 客房服务商品表
 *
 * @author xiehuiyi
 * @date 2020-04-23
 */
public class ZlServicegoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 所属分类ID
     */
    private Integer goodscategoryid;

    /**
     * 商品封面图片
     */
    private String coverimgurl;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 标签
     */
    private String tags;

    /**
     * 商品详情
     */
    private String content;

    /**
     * 0:下架;1上架
     */
    private Byte goodsstatus;

    /**
     * 每天每房间可领取次数
     */
    private Integer applylimitcount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public Integer getGoodscategoryid() {
        return goodscategoryid;
    }

    public void setGoodscategoryid(Integer goodscategoryid) {
        this.goodscategoryid = goodscategoryid;
    }

    public String getCoverimgurl() {
        return coverimgurl;
    }

    public void setCoverimgurl(String coverimgurl) {
        this.coverimgurl = coverimgurl;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getGoodsstatus() {
        return goodsstatus;
    }

    public void setGoodsstatus(Byte goodsstatus) {
        this.goodsstatus = goodsstatus;
    }

    public Integer getApplylimitcount() {
        return applylimitcount;
    }

    public void setApplylimitcount(Integer applylimitcount) {
        this.applylimitcount = applylimitcount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
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