package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房型信息
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_hotelroomtype")
public class ZlHotelroomtype implements Serializable {
    /**
     * 
     */
    private Integer roomtypeid;

    /**
     * 房型
     */
    private String roomtypename;

    /**
     * 介绍
     */
    private String description;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 浏览人数
     */
    private Integer visitcount;

    /**
     * 现价
     */
    private BigDecimal currentprice;

    /**
     * 原价
     */
    private BigDecimal originalprice;

    /**
     * 图片
     */
    private String banner;

    /**
     * 价格组
     */
    private String rankmoney;

    /**
     * 标签组(与tags表对应);多个用|隔开
     */
    private String tagids;

    /**
     * 房间大小
     */
    private String roomsize;

    /**
     * 适合居住人数
     */
    private Byte customercount;

    /**
     * 房间大小
     */
    private String bedsize;

    /**
     * 排序
     */
    private Byte sort;

    /**
     * 状态
     */
    private Byte status;

    /**
     *操作员
     */
    private String operatorname;

    /**
     *操作员IP
     */
    private String operatorip;

    /**
     *操作员备注
     */
    private String operatorremark;

    /**
     * 删除状态:0正常;1删除;
     */
    private String isdelete;

    /**
     * 
     */
    private Integer createdate;

    /**
     * 
     */
    private Integer updatedate;

}