package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店信息
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_hotel")
public class ZlHotel implements Serializable {
    /**
     *  酒店ID
     */
    private Integer hotelID;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * JoinType=1表示用户ID，2：表示s2合伙人ID
     */
    private Integer extID;

    /**
     * 前台电话(只有一个电话)
     */
    private String receptionTel;

    /**
     * 酒店简称
     */
    private String hotelNameAbbr;

    /**
     * 酒店核对人电话(也是联系人电话)
     */
    private String mobilePhone;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 最大允许挂账金额
     */
    private BigDecimal maxCreditMoney;

    /**
     * 终止合作时间
     */
    private Integer terminateDate;

    /**
     * 下架时间
     */
    private Integer pullOffDate;

    /**
     * 酒店图片
     */
    private String coverUrl;

    /**
     * 客服电话, 多个用|隔开
     */
    private String tel;

    /**
     * 类型:1酒店;2民宿
     */
    private Byte hotelType;

    /**
     * 酒店状态:0关闭;1启用
     */
    private Boolean hotelStatus;

    /**
     * 入驻类型：0：非入驻型；1：酒店自己入驻；2：通过S2入驻
     */
    private Integer joinType;

    /**
     * 入驻酒店合作状态：-1终止合作;1已正常入驻
     */
    private Integer joinStatus;


    /**
     * 所属入驻的用户、合伙人
     */
    private String joinUserName;


    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isDelete;

    /**
     * 更新日期
     */
    private Integer updateDate;

    /**
     * 添加日期
     */
    private Integer createDate;

    /**
     * 加入时间
     */
    private Integer joinDate;

    /**
     * 酒店台卡模板ID
     */
    private Integer qrcoderecid;

    /**
     * 旧ID
     */
    private Integer oldid;

    /**
     * 酒店完善步骤
     */
    private Byte step;

}