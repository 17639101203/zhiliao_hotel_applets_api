package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客房服务商品表
 *
 * @author xiehuiyi
 * @date 2020-04-23
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zl_servicegoods")
public class ZlServicegoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 所属分类ID
     */
    private Integer goodscategoryid;

    /**
     * 商品封面图片
     */
    private String coverimgurl;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 标签
     */
    private String tags;

    /**
     * 商品详情
     */
    private String content;

    /**
     * 一天可领取次数
     */
    private Integer daymaxcount;

    /**
     * 一天可领取数量
     */
    private Integer daymaxgoodscount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0:下架;1上架
     */
    private Byte goodsstatus;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    private Byte checkstatus;

    /**
     * 售价
     */
    private BigDecimal saleprice;

    /**
     * 原价
     */
    private BigDecimal originalprice;

    /**
     * 市场价
     */
    private BigDecimal marketprice;

    /**
     * 浏览量
     */
    private Integer visitcount;

    /**
     * 初始销量
     */
    private Integer virtualsoldcount;

    /**
     * 规格
     */
    private String property;

    /**
     * 首页显示：0否；1是
     */
    private Boolean isindexshow;

    /**
     * 添加人员
     */
    private String adminname;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 旧ID
     */
    private Integer oldid;

    /**
     * 操作员
     */
    private String operatorname;

    /**
     * 操作员IP
     */
    private String operatorip;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

}