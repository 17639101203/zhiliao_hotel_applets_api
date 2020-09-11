package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 轮播图
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_banner")
public class ZlBanner implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Column(name = "BannerID")
    private Integer bannerid;

    /**
     * 标题
     */
    @Column(name = "Title")
    private String title;

    /**
     * banner图
     */
    @Column(name = "BannerImg")
    private String bannerimg;

    /**
     * 操作人员名称
     */
    @Column(name = "OperatorUserName")
    private String operatorUserName;

    /**
     * banner链接地址
     */
    @Column(name = "LinkUrl")
    private String linkurl;

    /**
     * 显示状态0:不显示;1显示
     */
    @Column(name = "BannerStatus")
    private Boolean bannerstatus;

    /**
     * 所属酒店ID
     */
    @Column(name = "HotelID")
    private Integer hotelid;
    /**
     * 所属酒店菜单ID
     */
    @Column(name = "MenuID")
    private Integer menuid;

    /**
     * 操作人员IP
     */
    @Column(name = "OperatorIP")
    private String operatorIp;

    /**
     * 操作备注
     */
    @Column(name = "OperatorRemark")
    private String operatorRemark;

    /**
     * 点击量
     */
    @Column(name = "VisitCount")
    private Integer visitCount;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    @Column(name = "CheckStatus")
    private Integer checkStatus;

    /**
     * 视频时:1视频在banner中第1张;5表示最后一张
     */
    @Column(name = "VideoPosition")
    private Integer videoPosition;

    /**
     * 所属位置:1顶部，2是中部
     */
    @Column(name = "Position")
    private Byte position;

    /**
     * 1图片，2视频
     */
    @Column(name = "Type")
    private Boolean type;

    /**
     * 排序
     */
    @Column(name = "Sort")
    private Integer sort;

    /**
     * 删除状态:0正常;1删除;
     */
    @Column(name = "IsDelete")
    private Boolean isdelete;

    /**
     * 添加时间
     */
    @Column(name = "CreateDate")
    private Integer createdate;

    /**
     * 修改时间
     */
    @Column(name = "UpdateDate")
    private Integer updatedate;

    /**
     * 添加人
     */
    @Column(name = "AdminName")
    private Integer adminname;

    /**
     * 操作时间
     */
    @Column(name = "OperatorTime")
    private Integer operatortime;

}