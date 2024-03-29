package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 酒店小程序自定义菜单
 *
 * @author denghanchen
 * @date 2020-04-14
 */
public class ZlXcxmenucustom implements Serializable {
    /**
     * 
     */
    private Short id;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 菜单栏ID
     */
    private Integer menuid;

    /**
     * 功能菜单名称
     */
    private String menuname;

    /**
     * Url地址
     */
    private String linkurl;

    /**
     * 图标
     */
    private String iconurl;

    /**
     * 版本，-1所有版本，0中文，1英文，2繁体中文
     */
    private Byte version;

    /**
     * 服务时间段
     */
    private String serviceopentime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 商品编辑：0不可编辑1可编辑
     */
    private Boolean editstatus;

    /**
     * 状态:-1禁用;1启用
     */
    private Boolean menustatus;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_xcxmenucustom
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.ID
     *
     * @return the value of zl_xcxmenucustom.ID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.ID
     *
     * @param id the value for zl_xcxmenucustom.ID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.HotelID
     *
     * @return the value of zl_xcxmenucustom.HotelID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Integer getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.HotelID
     *
     * @param hotelid the value for zl_xcxmenucustom.HotelID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.MenuID
     *
     * @return the value of zl_xcxmenucustom.MenuID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Integer getMenuid() {
        return menuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.MenuID
     *
     * @param menuid the value for zl_xcxmenucustom.MenuID
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.MenuName
     *
     * @return the value of zl_xcxmenucustom.MenuName
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public String getMenuname() {
        return menuname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.MenuName
     *
     * @param menuname the value for zl_xcxmenucustom.MenuName
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.LinkUrl
     *
     * @return the value of zl_xcxmenucustom.LinkUrl
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public String getLinkurl() {
        return linkurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.LinkUrl
     *
     * @param linkurl the value for zl_xcxmenucustom.LinkUrl
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl == null ? null : linkurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.IconUrl
     *
     * @return the value of zl_xcxmenucustom.IconUrl
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public String getIconurl() {
        return iconurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.IconUrl
     *
     * @param iconurl the value for zl_xcxmenucustom.IconUrl
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setIconurl(String iconurl) {
        this.iconurl = iconurl == null ? null : iconurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.Version
     *
     * @return the value of zl_xcxmenucustom.Version
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Byte getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.Version
     *
     * @param version the value for zl_xcxmenucustom.Version
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.ServiceOpenTime
     *
     * @return the value of zl_xcxmenucustom.ServiceOpenTime
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public String getServiceopentime() {
        return serviceopentime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.ServiceOpenTime
     *
     * @param serviceopentime the value for zl_xcxmenucustom.ServiceOpenTime
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setServiceopentime(String serviceopentime) {
        this.serviceopentime = serviceopentime == null ? null : serviceopentime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.Sort
     *
     * @return the value of zl_xcxmenucustom.Sort
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.Sort
     *
     * @param sort the value for zl_xcxmenucustom.Sort
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.EditStatus
     *
     * @return the value of zl_xcxmenucustom.EditStatus
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Boolean getEditstatus() {
        return editstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.EditStatus
     *
     * @param editstatus the value for zl_xcxmenucustom.EditStatus
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setEditstatus(Boolean editstatus) {
        this.editstatus = editstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.MenuStatus
     *
     * @return the value of zl_xcxmenucustom.MenuStatus
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Boolean getMenustatus() {
        return menustatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.MenuStatus
     *
     * @param menustatus the value for zl_xcxmenucustom.MenuStatus
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setMenustatus(Boolean menustatus) {
        this.menustatus = menustatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.CreateDate
     *
     * @return the value of zl_xcxmenucustom.CreateDate
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.CreateDate
     *
     * @param createdate the value for zl_xcxmenucustom.CreateDate
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_xcxmenucustom.UpdateDate
     *
     * @return the value of zl_xcxmenucustom.UpdateDate
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public Integer getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_xcxmenucustom.UpdateDate
     *
     * @param updatedate the value for zl_xcxmenucustom.UpdateDate
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_xcxmenucustom
     *
     * @mbg.generated Tue Apr 14 11:18:29 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", menuid=").append(menuid);
        sb.append(", menuname=").append(menuname);
        sb.append(", linkurl=").append(linkurl);
        sb.append(", iconurl=").append(iconurl);
        sb.append(", version=").append(version);
        sb.append(", serviceopentime=").append(serviceopentime);
        sb.append(", sort=").append(sort);
        sb.append(", editstatus=").append(editstatus);
        sb.append(", menustatus=").append(menustatus);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}