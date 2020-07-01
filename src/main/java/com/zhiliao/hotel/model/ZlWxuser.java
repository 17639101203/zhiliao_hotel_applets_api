package com.zhiliao.hotel.model;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户表
 *
 * @author xiehuiui
 * @date 2020-04-14
 */
@Table(name="zl_wxuser")
public class ZlWxuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long userid;

    /**
     * 微信OpenID

     */
    private String wxopenid;

    /**
     * 微信UnionID
     */
    private String wxunionid;

    /**
     * 微信二维码Ticket
     */
    private String wxqrcodeticket;

    /**
     * 微信二维码上次更新时间
     */
    private Integer wxqrcodeupdatetime;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String headimgurl;

    /**
     * 性别：0未知，1男，2女
     */
    private Byte sex;

    /**
     * 是否新用户(是否领取过优惠券)
     */
    private Boolean isnew;

    /**
     * 当前余额(包括暂时不可提现部分)
     */
    private BigDecimal balance;

    /**
     * 可提现金额
     */
    private BigDecimal remainwithdrawcash;

    /**
     * 已提现金额
     */
    private BigDecimal withdrewcash;

    /**
     * 冻结中金额
     */
    private BigDecimal frozencash;

    /**
     * 积分
     */
    private Long score;

    /**
     * 是否完善资料
     */
    private Boolean isinfocompleted;

    /**
     * 所属酒店ID
     */
    private Integer hotelid;

    /**
     * 管理员ID
     */
    private Integer adminid;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 1:个人，2:管理员
     */
    private Byte roletype;

    /**
     * 会员等级
     */
    private Byte userlevel;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 修改日期
     */
    private Integer updatedate;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public String getWxunionid() {
        return wxunionid;
    }

    public void setWxunionid(String wxunionid) {
        this.wxunionid = wxunionid;
    }

    public String getWxqrcodeticket() {
        return wxqrcodeticket;
    }

    public void setWxqrcodeticket(String wxqrcodeticket) {
        this.wxqrcodeticket = wxqrcodeticket;
    }

    public Integer getWxqrcodeupdatetime() {
        return wxqrcodeupdatetime;
    }

    public void setWxqrcodeupdatetime(Integer wxqrcodeupdatetime) {
        this.wxqrcodeupdatetime = wxqrcodeupdatetime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Boolean getIsnew() {
        return isnew;
    }

    public void setIsnew(Boolean isnew) {
        this.isnew = isnew;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getRemainwithdrawcash() {
        return remainwithdrawcash;
    }

    public void setRemainwithdrawcash(BigDecimal remainwithdrawcash) {
        this.remainwithdrawcash = remainwithdrawcash;
    }

    public BigDecimal getWithdrewcash() {
        return withdrewcash;
    }

    public void setWithdrewcash(BigDecimal withdrewcash) {
        this.withdrewcash = withdrewcash;
    }

    public BigDecimal getFrozencash() {
        return frozencash;
    }

    public void setFrozencash(BigDecimal frozencash) {
        this.frozencash = frozencash;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Boolean getIsinfocompleted() {
        return isinfocompleted;
    }

    public void setIsinfocompleted(Boolean isinfocompleted) {
        this.isinfocompleted = isinfocompleted;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public Integer getComeformid() {
        return comeformid;
    }

    public void setComeformid(Integer comeformid) {
        this.comeformid = comeformid;
    }

    public Byte getRoletype() {
        return roletype;
    }

    public void setRoletype(Byte roletype) {
        this.roletype = roletype;
    }

    public Byte getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Byte userlevel) {
        this.userlevel = userlevel;
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