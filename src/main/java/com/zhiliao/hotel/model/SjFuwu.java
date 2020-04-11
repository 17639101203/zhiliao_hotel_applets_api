package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 酒店服务类
 *
 * @author xiehuiyi
 * @date 2020-04-11
 */
public class SjFuwu implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 所属ID
     */
    private Integer parentid;

    /**
     * 添加操作的帐号
     */
    private String addname;

    /**
     * 添加操作的ID
     */
    private Short addnameid;

    /**
     * 添加时间
     */
    private Integer addtime;

    /**
     * 添加操作的IP
     */
    private Integer addip;

    /**
     * 修改操作的帐号
     */
    private String eidtname;

    /**
     * 修改时间
     */
    private Integer eidttime;

    /**
     * 编辑操作的IP
     */
    private Integer eidtip;

    /**
     * 阅读次数
     */
    private Integer viewcount;

    /**
     * 送达天数
     */
    private Integer songdadays;

    /**
     * 库存量
     */
    private Integer count;

    /**
     * 已售数量
     */
    private Integer sales;

    /**
     * 排序
     */
    private Integer descs;

    /**
     * 所属酒店ID：0为本平台
     */
    private Integer jiudianid;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品标题
     */
    private String title;

    /**
     * 所属用户
     */
    private Integer userid;

    /**
     * 积分说明
     */
    private String use;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 售价
     */
    private Float price;

    /**
     * 区间价格
     */
    private Float endprice;

    /**
     * 积分
     */
    private Integer jifen;

    /**
     * 一级分润
     */
    private Float fenrun;

    /**
     * 一级分润
     */
    private Float fenrun2;

    /**
     * 原价
     */
    private Float oldprice;

    /**
     * 市场价
     */
    private Float shichangprice;

    /**
     * 平台价
     */
    private Float pingtaiprice;

    /**
     * 首数量
     */
    private Byte firstnum;

    /**
     * 首数量运费
     */
    private Float firstfreight;

    /**
     * 后续数量
     */
    private Byte pernum;

    /**
     * 后续数量运费
     */
    private Float perfreight;

    /**
     * 参数
     */
    private String param;

    /**
     * 是否显示到首页
     */
    private Byte showhome;

    /**
     * 是否上架
     */
    private Byte showflag;

    /**
     * 是否精选
     */
    private Byte showjingxuan;

    /**
     * 是否促销
     */
    private Byte showchuxiao;

    /**
     * 是否热卖
     */
    private Byte showhot;

    /**
     * 是否为预订产品
     */
    private Byte book;

    /**
     * 版本，0中文，1英文，2繁体中文
     */
    private Byte version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getAddname() {
        return addname;
    }

    public void setAddname(String addname) {
        this.addname = addname;
    }

    public Short getAddnameid() {
        return addnameid;
    }

    public void setAddnameid(Short addnameid) {
        this.addnameid = addnameid;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    public Integer getAddip() {
        return addip;
    }

    public void setAddip(Integer addip) {
        this.addip = addip;
    }

    public String getEidtname() {
        return eidtname;
    }

    public void setEidtname(String eidtname) {
        this.eidtname = eidtname;
    }

    public Integer getEidttime() {
        return eidttime;
    }

    public void setEidttime(Integer eidttime) {
        this.eidttime = eidttime;
    }

    public Integer getEidtip() {
        return eidtip;
    }

    public void setEidtip(Integer eidtip) {
        this.eidtip = eidtip;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }

    public Integer getSongdadays() {
        return songdadays;
    }

    public void setSongdadays(Integer songdadays) {
        this.songdadays = songdadays;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getDescs() {
        return descs;
    }

    public void setDescs(Integer descs) {
        this.descs = descs;
    }

    public Integer getJiudianid() {
        return jiudianid;
    }

    public void setJiudianid(Integer jiudianid) {
        this.jiudianid = jiudianid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getEndprice() {
        return endprice;
    }

    public void setEndprice(Float endprice) {
        this.endprice = endprice;
    }

    public Integer getJifen() {
        return jifen;
    }

    public void setJifen(Integer jifen) {
        this.jifen = jifen;
    }

    public Float getFenrun() {
        return fenrun;
    }

    public void setFenrun(Float fenrun) {
        this.fenrun = fenrun;
    }

    public Float getFenrun2() {
        return fenrun2;
    }

    public void setFenrun2(Float fenrun2) {
        this.fenrun2 = fenrun2;
    }

    public Float getOldprice() {
        return oldprice;
    }

    public void setOldprice(Float oldprice) {
        this.oldprice = oldprice;
    }

    public Float getShichangprice() {
        return shichangprice;
    }

    public void setShichangprice(Float shichangprice) {
        this.shichangprice = shichangprice;
    }

    public Float getPingtaiprice() {
        return pingtaiprice;
    }

    public void setPingtaiprice(Float pingtaiprice) {
        this.pingtaiprice = pingtaiprice;
    }

    public Byte getFirstnum() {
        return firstnum;
    }

    public void setFirstnum(Byte firstnum) {
        this.firstnum = firstnum;
    }

    public Float getFirstfreight() {
        return firstfreight;
    }

    public void setFirstfreight(Float firstfreight) {
        this.firstfreight = firstfreight;
    }

    public Byte getPernum() {
        return pernum;
    }

    public void setPernum(Byte pernum) {
        this.pernum = pernum;
    }

    public Float getPerfreight() {
        return perfreight;
    }

    public void setPerfreight(Float perfreight) {
        this.perfreight = perfreight;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Byte getShowhome() {
        return showhome;
    }

    public void setShowhome(Byte showhome) {
        this.showhome = showhome;
    }

    public Byte getShowflag() {
        return showflag;
    }

    public void setShowflag(Byte showflag) {
        this.showflag = showflag;
    }

    public Byte getShowjingxuan() {
        return showjingxuan;
    }

    public void setShowjingxuan(Byte showjingxuan) {
        this.showjingxuan = showjingxuan;
    }

    public Byte getShowchuxiao() {
        return showchuxiao;
    }

    public void setShowchuxiao(Byte showchuxiao) {
        this.showchuxiao = showchuxiao;
    }

    public Byte getShowhot() {
        return showhot;
    }

    public void setShowhot(Byte showhot) {
        this.showhot = showhot;
    }

    public Byte getBook() {
        return book;
    }

    public void setBook(Byte book) {
        this.book = book;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}