package com.zhiliao.hotel.model;

import java.io.Serializable;

/**
 * 酒店服务父类
 *
 * @author xiehuiyi
 * @date 2020-04-11
 */
public class SjFuwuparent implements Serializable {
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
     * 排序
     */
    private Integer descs;

    /**
     * 所属酒店ID：0为本平台
     */
    private Integer jiudianid;

    /**
     * 类别名称
     */
    private String title;

    /**
     * 是否开启，0：关闭，1开启
     */
    private Byte showflag;

    /**
     * 窗口打开方式
     */
    private String target;

    /**
     * Url地址
     */
    private String url;

    /**
     * 上传文件路径
     */
    private String filepath;

    /**
     * 上传文件名称
     */
    private String filename;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getShowflag() {
        return showflag;
    }

    public void setShowflag(Byte showflag) {
        this.showflag = showflag;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}