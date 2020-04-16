package com.zhiliao.hotel.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 轮播图
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Table(name = "zl_banner")
public class ZlBanner implements Serializable {
    /**
     * 主键
     */
    private Integer bannerid;

    /**
     * banner图
     */
    private String bannerimg;

    /**
     * banner链接地址
     */
    private String linkurl;

    /**
     * 显示状态0:不显示;1显示
     */
    private Boolean bannerstatus;

    /**
     * 所属酒店ID
     */
    @Column(name = "HotelID")
    private Integer hotelid;
    /**
     * 所属酒店菜单ID
     */
    @Column(name = "MenuID")
    private Integer menuid;


    /**
     * 所属位置:1顶部，2是中部
     */
    private Byte position;

    /**
     * 1图片，2视频
     */
    private Boolean type;

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


    private static final long serialVersionUID = 1L;


    public Integer getBannerid() {
        return bannerid;
    }


    public void setBannerid(Integer bannerid) {
        this.bannerid = bannerid;
    }


    public String getBannerimg() {
        return bannerimg;
    }


    public void setBannerimg(String bannerimg) {
        this.bannerimg = bannerimg == null ? null : bannerimg.trim();
    }


    public String getLinkurl() {
        return linkurl;
    }


    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl == null ? null : linkurl.trim();
    }


    public Boolean getBannerstatus() {
        return bannerstatus;
    }


    public void setBannerstatus(Boolean bannerstatus) {
        this.bannerstatus = bannerstatus;
    }


    public Integer getHotelid() {
        return hotelid;
    }


    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }


    public Integer getMenuid() {
        return menuid;
    }


    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }


    public Byte getPosition() {
        return position;
    }


    public void setPosition(Byte position) {
        this.position = position;
    }


    public Boolean getType() {
        return type;
    }


    public void setType(Boolean type) {
        this.type = type;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bannerid=").append(bannerid);
        sb.append(", bannerimg=").append(bannerimg);
        sb.append(", linkurl=").append(linkurl);
        sb.append(", bannerstatus=").append(bannerstatus);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", menuid=").append(menuid);
        sb.append(", position=").append(position);
        sb.append(", type=").append(type);
        sb.append(", sort=").append(sort);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}