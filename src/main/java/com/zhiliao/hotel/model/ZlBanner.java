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


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Column(name="BannerID")
    private Integer bannerid;


    /**
     * 标题
     */
    @Column(name="Title")
    private String title;

    /**
     * banner图
     */
    @Column(name="BannerImg")
    private String bannerimg;

    /**
     * 操作人员名称
     */
    @Column(name="OperatorUserName")
    private String operatorUserName;

    /**
     * banner链接地址
     */
    @Column(name="LinkUrl")
    private String linkurl;

    /**
     * 显示状态0:不显示;1显示
     */
    @Column(name="BannerStatus")
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
     * 操作人员IP
     */
    @Column(name="OperatorIP")
    private String operatorIp;

    /**
     * 操作备注
     */
    @Column(name="OperatorRemark")
    private String operatorRemark;

    /**
     * 点击量
     */
    @Column(name="VisitCount")
    private Integer visitCount;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    @Column(name="CheckStatus")
    private Integer checkStatus;

    /**
     * 视频时:1视频在banner中第1张;5表示最后一张
     */
    @Column(name="VideoPosition")
    private Integer videoPosition;

    /**
     * 所属位置:1顶部，2是中部
     */
    @Column(name="Position")
    private Byte position;

    /**
     * 1图片，2视频
     */
    @Column(name="Type")
    private Boolean type;

    /**
     * 排序
     */
    @Column(name="Sort")
    private Integer sort;

    /**
     * 删除状态:0正常;1删除;
     */
    @Column(name="IsDelete")
    private Boolean isdelete;

    /**
     * 添加时间
     */
    @Column(name="CreateDate")
    private Integer createdate;

    /**
     * 修改时间
     */
    @Column(name="UpdateDate")
    private Integer updatedate;



    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(Integer videoPosition) {
        this.videoPosition = videoPosition;
    }


    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public String getOperatorRemark() {
        return operatorRemark;
    }

    public void setOperatorRemark(String operatorRemark) {
        this.operatorRemark = operatorRemark;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }


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