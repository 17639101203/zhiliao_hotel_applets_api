package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-04-26
 */
public class ZlGoodsCategory implements Serializable {
    /**
     *  分类ID
     */
    private Integer goodscategoryid;

    /**
     *  分类名称:酒水/酒店/零食...
     */
    private String categoryname;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 所属模块 1:客房服务;2便利店;3餐饮服务;4情趣用品;5土特产
     */
    private Short belongmodule;

    /**
     * 分类状态 0未启用;1启用
     */
    private Byte categorystatus;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_goodscategory
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.GoodsCategoryID
     *
     * @return the value of zl_goodscategory.GoodsCategoryID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Integer getGoodscategoryid() {
        return goodscategoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.GoodsCategoryID
     *
     * @param goodscategoryid the value for zl_goodscategory.GoodsCategoryID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setGoodscategoryid(Integer goodscategoryid) {
        this.goodscategoryid = goodscategoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.CategoryName
     *
     * @return the value of zl_goodscategory.CategoryName
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public String getCategoryname() {
        return categoryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.CategoryName
     *
     * @param categoryname the value for zl_goodscategory.CategoryName
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname == null ? null : categoryname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.HotelID
     *
     * @return the value of zl_goodscategory.HotelID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Integer getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.HotelID
     *
     * @param hotelid the value for zl_goodscategory.HotelID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.SupplierID
     *
     * @return the value of zl_goodscategory.SupplierID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Integer getSupplierid() {
        return supplierid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.SupplierID
     *
     * @param supplierid the value for zl_goodscategory.SupplierID
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.BelongModule
     *
     * @return the value of zl_goodscategory.BelongModule
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Short getBelongmodule() {
        return belongmodule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.BelongModule
     *
     * @param belongmodule the value for zl_goodscategory.BelongModule
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setBelongmodule(Short belongmodule) {
        this.belongmodule = belongmodule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.CategoryStatus
     *
     * @return the value of zl_goodscategory.CategoryStatus
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Byte getCategorystatus() {
        return categorystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.CategoryStatus
     *
     * @param categorystatus the value for zl_goodscategory.CategoryStatus
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setCategorystatus(Byte categorystatus) {
        this.categorystatus = categorystatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.Sort
     *
     * @return the value of zl_goodscategory.Sort
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Short getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.Sort
     *
     * @param sort the value for zl_goodscategory.Sort
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.IsDelete
     *
     * @return the value of zl_goodscategory.IsDelete
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.IsDelete
     *
     * @param isdelete the value for zl_goodscategory.IsDelete
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.CreateDate
     *
     * @return the value of zl_goodscategory.CreateDate
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.CreateDate
     *
     * @param createdate the value for zl_goodscategory.CreateDate
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_goodscategory.UpdateDate
     *
     * @return the value of zl_goodscategory.UpdateDate
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public Integer getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_goodscategory.UpdateDate
     *
     * @param updatedate the value for zl_goodscategory.UpdateDate
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_goodscategory
     *
     * @mbg.generated Sun Apr 26 10:23:43 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodscategoryid=").append(goodscategoryid);
        sb.append(", categoryname=").append(categoryname);
        sb.append(", hotelid=").append(hotelid);
        sb.append(", supplierid=").append(supplierid);
        sb.append(", belongmodule=").append(belongmodule);
        sb.append(", categorystatus=").append(categorystatus);
        sb.append(", sort=").append(sort);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}