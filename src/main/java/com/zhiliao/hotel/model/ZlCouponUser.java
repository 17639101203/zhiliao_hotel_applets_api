package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 
 *
 * @author null
 * @date 2020-05-25
 */
public class ZlCouponUser implements Serializable {
    /**
     * 自增ID
     */
    private Integer recid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 优惠券ID
     */
    private Integer couponid;

    /**
     * 0未使用；1已使用
     */
    private Boolean isused;

    /**
     * 过期时间
     */
    private Integer expireddate;

    /**
     * 使用日期
     */
    private Integer usedate;

    /**
     * 使用在哪个订单上
     */
    private Integer orderid;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 领取时间
     */
    private Integer createdate;

    private String couponname;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_couponuser
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    private static final long serialVersionUID = 1L;

    public String getCouponname() {
        return couponname;
    }

    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.RecID
     *
     * @return the value of zl_couponuser.RecID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getRecid() {
        return recid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.RecID
     *
     * @param recid the value for zl_couponuser.RecID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setRecid(Integer recid) {
        this.recid = recid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.UserID
     *
     * @return the value of zl_couponuser.UserID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.UserID
     *
     * @param userid the value for zl_couponuser.UserID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.CouponID
     *
     * @return the value of zl_couponuser.CouponID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getCouponid() {
        return couponid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.CouponID
     *
     * @param couponid the value for zl_couponuser.CouponID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setCouponid(Integer couponid) {
        this.couponid = couponid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.IsUsed
     *
     * @return the value of zl_couponuser.IsUsed
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Boolean getIsused() {
        return isused;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.IsUsed
     *
     * @param isused the value for zl_couponuser.IsUsed
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setIsused(Boolean isused) {
        this.isused = isused;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.ExpiredDate
     *
     * @return the value of zl_couponuser.ExpiredDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getExpireddate() {
        return expireddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.ExpiredDate
     *
     * @param expireddate the value for zl_couponuser.ExpiredDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setExpireddate(Integer expireddate) {
        this.expireddate = expireddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.UseDate
     *
     * @return the value of zl_couponuser.UseDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getUsedate() {
        return usedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.UseDate
     *
     * @param usedate the value for zl_couponuser.UseDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setUsedate(Integer usedate) {
        this.usedate = usedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.OrderID
     *
     * @return the value of zl_couponuser.OrderID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.OrderID
     *
     * @param orderid the value for zl_couponuser.OrderID
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.IsDelete
     *
     * @return the value of zl_couponuser.IsDelete
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.IsDelete
     *
     * @param isdelete the value for zl_couponuser.IsDelete
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_couponuser.CreateDate
     *
     * @return the value of zl_couponuser.CreateDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public Integer getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zl_couponuser.CreateDate
     *
     * @param createdate the value for zl_couponuser.CreateDate
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_couponuser
     *
     * @mbg.generated Mon May 25 15:01:52 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recid=").append(recid);
        sb.append(", userid=").append(userid);
        sb.append(", couponid=").append(couponid);
        sb.append(", isused=").append(isused);
        sb.append(", expireddate=").append(expireddate);
        sb.append(", usedate=").append(usedate);
        sb.append(", orderid=").append(orderid);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", createdate=").append(createdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}