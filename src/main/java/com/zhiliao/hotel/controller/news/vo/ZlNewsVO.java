package com.zhiliao.hotel.controller.news.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-11 11:48
 **/
@Data
public class ZlNewsVO {

    /**
     * 主键
     */
    private Integer newsid;

    /**
     * 酒店id; id=0时为系统新闻或系统公告
     */
    private Integer hotelid;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 1:公告;2新闻资讯
     */
    private Byte type;

    /**
     * 作者
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 缩略图
     */
    private String imgurl;

    /**
     * 阅读数
     */
    private Integer visitcount;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 是否启用 0:否;1:是
     */
    private Boolean status;

    public Integer getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(Integer checkstatus) {
        this.checkstatus = checkstatus;
    }

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    private Integer checkstatus;

    /**
     * 创建人
     */
    private String creatorusername;

    /**
     * 添加ip
     */
    private String creatorusrip;

    /**
     * 修改人
     */
    private String modifyusername;

    /**
     * 修改ip
     */
    private String modifyuserip;

    /**
     * 新闻时间
     */
    private Integer createdate;


    /**
     * 修改时间
     */
    private Integer updatedate;

    private String operatorremark;
    /**
     * 内容
     */
    private String content;

}
