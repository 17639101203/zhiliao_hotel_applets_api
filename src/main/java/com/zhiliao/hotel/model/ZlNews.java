package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author null
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_news")
public class ZlNews implements Serializable {
    /**
     * 主键
     */
    private Integer newsid;

    /**
     * 酒店id; id=0时为系统新闻或系统公告
     */
    private Integer hotelid;

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
     * 内容
     */
    private String content;

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
     * 审核通过自动上架 0:否 1:是
     */
    private Byte autoup;

    /**
     * 上下架状态: 0下架;1上架
     */
    private Byte status;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    private Byte checkstatus;

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
     * 操作备注
     */
    private String operatorremark;

    /**
     * 删除状态:0正常;1删除;
     */
    private Integer isDelete;

    /**
     * 新闻时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 审核时间
     */
    private Integer operatime;

    /**
     * 审核时间
     */
    private Integer operatortime;

    /**
     * 操作人
     */
    private String operatorusername;

    /**
     * 操作IP
     */
    private String operatorip;

    /**
     * 操作IP
     */
    private String hotelName;

}