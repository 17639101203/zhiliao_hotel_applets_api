package com.zhiliao.hotel.controller.hotel.in;

import com.zhiliao.hotel.model.*;
import org.springframework.beans.BeanUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @chenrong
 * @created 2020/4/17
 */

public class ZlHotelIn implements Serializable {


    public ZlHotelIn(){
        super();
    }

    public ZlHotelIn(ZlHotel zlHotel){
        BeanUtils.copyProperties(zlHotel,this);
    }



    /**
     *  酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名称
     */
    private String hotelname;

    /**
     *
     */
    private String coverurl;

    /**
     * 客服电话, 多个用|隔开
     */
    private String tel;

    /**
     * 省编号(对应area表AreaID)
     */
    private Short provincecode;

    /**
     * 市编号(对应area表)
     */
    private Short citycode;

    /**
     * 区编号(对应area表)
     */
    private Short districtcode;

    /**
     * 酒店详细地址
     */
    private String address;

    /**
     * 最大允许挂账金额
     */
    private BigDecimal maxcreditcash;

    /**
     * 类型:1酒店;2民宿
     */
    private Byte hoteltype;

    /**
     * 酒店状态:0关闭;1启用
     */
    private Boolean hotelstatus;

    /**
     * 经度
     */
    private Float longitude;

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 结算方式 百分比
     */
    private Float settlement;

    /**
     * 起送金额
     */
    private Long minsendcash;

    /**
     * 配送金额
     */
    private Long sendcash;

    /**
     * 合约图片,存储多个
     */
    private String contractimgurls;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 开户行账号
     */
    private String bankaccountnumber;

    /**
     * 户名
     */
    private String bankaccountname;

    /**
     * 今天的利润
     */
    private BigDecimal todaymoney;

    /**
     * 总可提现的利润
     */
    private BigDecimal allmoney;

    /**
     * 天气设置
     */
    private String weather;

    /**
     * 是否是S2入驻，0不是，其他S2ID
     */
    private Integer isjoins2;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 更新日期
     */
    private Integer updatedate;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zl_hotel
     *
     * @mbg.generated Tue Apr 14 11:22:18 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zl_hotel.HotelID
     *
     * @return the value of zl_hotel.HotelID
     *
     * @mbg.generated Tue Apr 14 11:22:18 CST 2020
     */


    /**
     * banner集合
     */
    private List<ZlBanner> zlBannerList;

    /**
     * 菜单集合
     */
    private List<ZlXcxmenu>zlXcxmenus;

    /**
     * 酒店客房信息
     */
    private ZlHotelroom hotelroom;

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Short getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(Short provincecode) {
        this.provincecode = provincecode;
    }

    public Short getCitycode() {
        return citycode;
    }

    public void setCitycode(Short citycode) {
        this.citycode = citycode;
    }

    public Short getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(Short districtcode) {
        this.districtcode = districtcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getMaxcreditcash() {
        return maxcreditcash;
    }

    public void setMaxcreditcash(BigDecimal maxcreditcash) {
        this.maxcreditcash = maxcreditcash;
    }

    public Byte getHoteltype() {
        return hoteltype;
    }

    public void setHoteltype(Byte hoteltype) {
        this.hoteltype = hoteltype;
    }

    public Boolean getHotelstatus() {
        return hotelstatus;
    }

    public void setHotelstatus(Boolean hotelstatus) {
        this.hotelstatus = hotelstatus;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getSettlement() {
        return settlement;
    }

    public void setSettlement(Float settlement) {
        this.settlement = settlement;
    }

    public Long getMinsendcash() {
        return minsendcash;
    }

    public void setMinsendcash(Long minsendcash) {
        this.minsendcash = minsendcash;
    }

    public Long getSendcash() {
        return sendcash;
    }

    public void setSendcash(Long sendcash) {
        this.sendcash = sendcash;
    }

    public String getContractimgurls() {
        return contractimgurls;
    }

    public void setContractimgurls(String contractimgurls) {
        this.contractimgurls = contractimgurls;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    public void setBankaccountnumber(String bankaccountnumber) {
        this.bankaccountnumber = bankaccountnumber;
    }

    public String getBankaccountname() {
        return bankaccountname;
    }

    public void setBankaccountname(String bankaccountname) {
        this.bankaccountname = bankaccountname;
    }

    public BigDecimal getTodaymoney() {
        return todaymoney;
    }

    public void setTodaymoney(BigDecimal todaymoney) {
        this.todaymoney = todaymoney;
    }

    public BigDecimal getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(BigDecimal allmoney) {
        this.allmoney = allmoney;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getIsjoins2() {
        return isjoins2;
    }

    public void setIsjoins2(Integer isjoins2) {
        this.isjoins2 = isjoins2;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Integer updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<ZlBanner> getZlBannerList() {
        return zlBannerList;
    }

    public void setZlBannerList(List<ZlBanner> zlBannerList) {
        this.zlBannerList = zlBannerList;
    }

    public List<ZlXcxmenu> getZlXcxmenus() {
        return zlXcxmenus;
    }

    public void setZlXcxmenus(List<ZlXcxmenu> zlXcxmenus) {
        this.zlXcxmenus = zlXcxmenus;
    }

    public ZlHotelroom getHotelroom() {
        return hotelroom;
    }

    public void setHotelroom(ZlHotelroom hotelroom) {
        this.hotelroom = hotelroom;
    }

    public List<ZlNews> getZlNews() {
        return zlNews;
    }

    public void setZlNews(List<ZlNews> zlNews) {
        this.zlNews = zlNews;
    }

    /**
     * 酒店公告信息
     */
    private List<ZlNews> zlNews;

}
