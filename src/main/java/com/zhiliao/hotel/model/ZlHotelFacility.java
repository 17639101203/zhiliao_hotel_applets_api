package com.zhiliao.hotel.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author null
 * @date 2020-05-25
 */
@Data
public class ZlHotelFacility implements Serializable {
    /**
     * 设施ID
     */
    private Integer facilityid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 设施名称(项目类别)
     */
    private String facilityname;

    /**
     * 设施数量
     */
    private Integer facilitycount;

    /**
     * 剩余数量
     */
    private Integer facilityremaincount;

    /**
     * 使用人数
     */
    private Integer allowpersoncount;

    /**
     * 设施图片
     */
    private String imgurl;

    /**
     * 开始时间
     */
    private Integer servicebegindate;

    /**
     * 结束时间
     */
    private Integer serviceenddate;

    /**
     * 使用时间(分钟)
     */
    private Integer useminute;

    /**
     * 使用次数
     */
    private Integer usecount;

    /**
     * 可取消订单时间(分钟)
     */
    private Integer cancancelorderminute;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 0未启用；1启用
     */
    private Byte facilitystatus;

    /**
     * 审核状态:-1拒绝;0待提交;1待审核;2审核通过
     */
    private Byte checkstatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 预定金额
     */
    private BigDecimal bookmoney;

    /**
     * 收费方式:1-按次,2-按小时
     */
    private Byte chargemethod;

    /**
     * 每次使用分钟数
     */
    private Integer onceuseminute;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 更新时间
     */
    private Integer updatedate;

    /**
     * 详情
     */
    private String content;

}