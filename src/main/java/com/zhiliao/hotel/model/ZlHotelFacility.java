package com.zhiliao.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author null
 * @date 2020-05-25
 */
public class ZlHotelFacility implements Serializable {
    /**
     * 设施ID
     */
    private Integer facilityid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 设施名称(项目类别)
     */
    private String facilityname;

    /**
     * 设施数量
     */
    private Integer facilitycount;

    /**
     * 剩余数量
     */
    private Integer facilityremaincount;

    /**
     * 使用人数
     */
    private Integer allowpersoncount;

    /**
     * 设施图片
     */
    private String imgurl;

    /**
     * 开始时间
     */
    private Integer servicebegindate;

    /**
     * 结束时间
     */
    private Integer serviceenddate;

    /**
     * 使用时间(分钟)
     */
    private Integer useminute;

    /**
     * 使用次数
     */
    private Integer usecount;

    /**
     * 可取消订单时间(分钟)
     */
    private Integer cancancelorderminute;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 0未启用；1启用
     */
    private Byte facilitystatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 预定金额
     */
    private BigDecimal bookmoney;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

    /**
     * 详情
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_hotelfacility
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.FacilityID
     *
     * @return the value of zl_hotelfacility.FacilityID
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getFacilityid() {
        return facilityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.FacilityID
     *
     * @param facilityid the value for zl_hotelfacility.FacilityID
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setFacilityid(Integer facilityid) {
        this.facilityid = facilityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.HotelID
     *
     * @return the value of zl_hotelfacility.HotelID
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.HotelID
     *
     * @param hotelid the value for zl_hotelfacility.HotelID
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.FacilityName
     *
     * @return the value of zl_hotelfacility.FacilityName
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public String getFacilityname() {
        return facilityname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.FacilityName
     *
     * @param facilityname the value for zl_hotelfacility.FacilityName
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname == null ? null : facilityname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.FacilityCount
     *
     * @return the value of zl_hotelfacility.FacilityCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getFacilitycount() {
        return facilitycount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.FacilityCount
     *
     * @param facilitycount the value for zl_hotelfacility.FacilityCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setFacilitycount(Integer facilitycount) {
        this.facilitycount = facilitycount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.FacilityRemainCount
     *
     * @return the value of zl_hotelfacility.FacilityRemainCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getFacilityremaincount() {
        return facilityremaincount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.FacilityRemainCount
     *
     * @param facilityremaincount the value for zl_hotelfacility.FacilityRemainCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setFacilityremaincount(Integer facilityremaincount) {
        this.facilityremaincount = facilityremaincount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.AllowPersonCount
     *
     * @return the value of zl_hotelfacility.AllowPersonCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getAllowpersoncount() {
        return allowpersoncount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.AllowPersonCount
     *
     * @param allowpersoncount the value for zl_hotelfacility.AllowPersonCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setAllowpersoncount(Integer allowpersoncount) {
        this.allowpersoncount = allowpersoncount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.ImgUrl
     *
     * @return the value of zl_hotelfacility.ImgUrl
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.ImgUrl
     *
     * @param imgurl the value for zl_hotelfacility.ImgUrl
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public Integer getServicebegindate() {
        return servicebegindate;
    }

    public void setServicebegindate(Integer servicebegindate) {
        this.servicebegindate = servicebegindate;
    }

    public Integer getServiceenddate() {
        return serviceenddate;
    }

    public void setServiceenddate(Integer serviceenddate) {
        this.serviceenddate = serviceenddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.UseMinute
     *
     * @return the value of zl_hotelfacility.UseMinute
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getUseminute() {
        return useminute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.UseMinute
     *
     * @param useminute the value for zl_hotelfacility.UseMinute
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setUseminute(Integer useminute) {
        this.useminute = useminute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.UseCount
     *
     * @return the value of zl_hotelfacility.UseCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getUsecount() {
        return usecount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.UseCount
     *
     * @param usecount the value for zl_hotelfacility.UseCount
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setUsecount(Integer usecount) {
        this.usecount = usecount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.CanCancelOrderMinute
     *
     * @return the value of zl_hotelfacility.CanCancelOrderMinute
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getCancancelorderminute() {
        return cancancelorderminute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.CanCancelOrderMinute
     *
     * @param cancancelorderminute the value for zl_hotelfacility.CanCancelOrderMinute
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setCancancelorderminute(Integer cancancelorderminute) {
        this.cancancelorderminute = cancancelorderminute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.Price
     *
     * @return the value of zl_hotelfacility.Price
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.Price
     *
     * @param price the value for zl_hotelfacility.Price
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.FacilityStatus
     *
     * @return the value of zl_hotelfacility.FacilityStatus
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Byte getFacilitystatus() {
        return facilitystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.FacilityStatus
     *
     * @param facilitystatus the value for zl_hotelfacility.FacilityStatus
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setFacilitystatus(Byte facilitystatus) {
        this.facilitystatus = facilitystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.Sort
     *
     * @return the value of zl_hotelfacility.Sort
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.Sort
     *
     * @param sort the value for zl_hotelfacility.Sort
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.BookMoney
     *
     * @return the value of zl_hotelfacility.BookMoney
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public BigDecimal getBookmoney() {
        return bookmoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.BookMoney
     *
     * @param bookmoney the value for zl_hotelfacility.BookMoney
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setBookmoney(BigDecimal bookmoney) {
        this.bookmoney = bookmoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.IsDelete
     *
     * @return the value of zl_hotelfacility.IsDelete
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.IsDelete
     *
     * @param isdelete the value for zl_hotelfacility.IsDelete
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.CreateDate
     *
     * @return the value of zl_hotelfacility.CreateDate
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.CreateDate
     *
     * @param createdate the value for zl_hotelfacility.CreateDate
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.UpdateDate
     *
     * @return the value of zl_hotelfacility.UpdateDate
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public Integer getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.UpdateDate
     *
     * @param updatedate the value for zl_hotelfacility.UpdateDate
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotelfacility.Content
     *
     * @return the value of zl_hotelfacility.Content
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_hotelfacility.Content
     *
     * @param content the value for zl_hotelfacility.Content
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_hotelfacility
     *
     * @mbg.generated Mon May 25 14:25:14 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", facilityid=").append(facilityid);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", facilityname=").append(facilityname);
        sb.append(", facilitycount=").append(facilitycount);
        sb.append(", facilityremaincount=").append(facilityremaincount);
        sb.append(", allowpersoncount=").append(allowpersoncount);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", servicebegindate=").append(servicebegindate);
        sb.append(", serviceenddate=").append(serviceenddate);
        sb.append(", useminute=").append(useminute);
        sb.append(", usecount=").append(usecount);
        sb.append(", cancancelorderminute=").append(cancancelorderminute);
        sb.append(", price=").append(price);
        sb.append(", facilitystatus=").append(facilitystatus);
        sb.append(", sort=").append(sort);
        sb.append(", bookmoney=").append(bookmoney);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}