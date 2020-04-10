package com.zhiliao.hotel.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 微信用户类
 *
 * @author xiehuiyi
 * @date 2020-04-09
 */
public class SjWeixinuser {
    /**
     *
     */
    private Integer id;

    /**
     * 微信OpenID
     */
    private String openid;

    /**
     * 微信UnionID
     */
    private String unionid;

    /**
     * 微信二维码Ticket
     */
    private String wxqrcodeticket;

    /**
     * 微信二维码上次更新时间
     */
    private Integer wxqrcodeupdatatime;

    /**
     * 加入时间
     */
    private Long addtime;

    /**
     * 更新时间
     */
    private Long updatatime;

    /**
     * 状态，0未绑定，1绑定，-1取消关注
     */
    private Byte status;

    /**
     * 微信昵称
     */
    private String nicknname;

    /**
     * 头像
     */
    private String headimgurl;

    /**
     * 性别：0未知，1男，2女
     */
    private Byte sex;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 是否关注：0未关注，1已关注
     */
    private Byte subscribe;

    /**
     * 余额
     */
    private BigDecimal remainder;

    /**
     * 积分
     */
    private Integer jifen;

    /**
     * 是否是新用户
     */
    private Boolean isNew;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户电话
     */
    private String tel;

    /**
     *
     */
    private Date birthday;

    /**
     * 城市
     */
    private String citys;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 可提现金额
     */
    private BigDecimal money;

    /**
     * 已提现金额
     */
    private BigDecimal cashmoney;

    /**
     * 是否完善资料
     */
    private Boolean perfect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getWxqrcodeticket() {
        return wxqrcodeticket;
    }

    public void setWxqrcodeticket(String wxqrcodeticket) {
        this.wxqrcodeticket = wxqrcodeticket;
    }

    public Integer getWxqrcodeupdatatime() {
        return wxqrcodeupdatatime;
    }

    public void setWxqrcodeupdatatime(Integer wxqrcodeupdatatime) {
        this.wxqrcodeupdatatime = wxqrcodeupdatatime;
    }

    public Long getAddtime() {
        return addtime;
    }

    public void setAddtime(Long addtime) {
        this.addtime = addtime;
    }

    public Long getUpdatatime() {
        return updatatime;
    }

    public void setUpdatatime(Long updatatime) {
        this.updatatime = updatatime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getNicknname() {
        return nicknname;
    }

    public void setNicknname(String nicknname) {
        this.nicknname = nicknname;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Byte getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Byte subscribe) {
        this.subscribe = subscribe;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }

    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }

    public Integer getJifen() {
        return jifen;
    }

    public void setJifen(Integer jifen) {
        this.jifen = jifen;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getCashmoney() {
        return cashmoney;
    }

    public void setCashmoney(BigDecimal cashmoney) {
        this.cashmoney = cashmoney;
    }

    public Boolean getPerfect() {
        return perfect;
    }

    public void setPerfect(Boolean perfect) {
        this.perfect = perfect;
    }
}