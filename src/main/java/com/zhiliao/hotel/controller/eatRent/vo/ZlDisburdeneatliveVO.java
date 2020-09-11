package com.zhiliao.hotel.controller.eatRent.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-09-07 20:23
 **/
@Data
public class ZlDisburdeneatliveVO {

    /**
     * 自增ID
     */
    private Integer recid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 浮动值
     */
    private Integer floatvalue;

    /**
     * 住户情况统计最小值
     */
    private Integer randomminval;

    /**
     * 住户情况统计最大值
     */
    private Integer randommaxval;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 用户ID
     */
    private Integer adminid;

    /**
     * 操作人员(审核人员)
     */
    private String operatorusername;

    /**
     * 作者
     */
    private String adminname;

    /**
     * 操作人员IP
     */
    private String operatorip;

    /**
     * 操作备注(审核备注)
     */
    private String operatorremark;

    /**
     * 上下架状态:0:下架;1上架
     */
    private Byte eatlivestatus;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过0:下架;1上架
     */
    private Byte checkstatus;

    /**
     * 阅读数
     */
    private Integer visitcount;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 清扫情况
     */
    private String cleansituation;

    /**
     * 布草情况
     */
    private String setupsituation;

    /**
     * 食品安全
     */
    private String goodssituation;

    /**
     * 审核时间
     */
    private Integer operatime;

    /**
     * 入住用户数
     */
    private Integer checkInCount;

}
