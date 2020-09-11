package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户表
 *
 * @author xiehuiui
 * @date 2020-04-14
 */
@Table(name="zl_wxuser")
@Data
public class ZlWxuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long userid;

    /**
     * 微信OpenID

     */
    private String wxopenid;

    /**
     * 微信UnionID
     */
    private String wxunionid;

    /**
     * 微信二维码Ticket
     */
    private String wxqrcodeticket;

    /**
     * 微信二维码上次更新时间
     */
    private Integer wxqrcodeupdatetime;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String headimgurl;

    /**
     * 性别：0未知，1男，2女
     */
    private Byte sex;

    /**
     * 是否新用户(是否领取过优惠券)
     */
    private Boolean isnew;

    /**
     * 当前余额(包括暂时不可提现部分)
     */
    private BigDecimal balance;

    /**
     * 可提现金额
     */
    private BigDecimal remainwithdrawcash;

    /**
     * 已提现金额
     */
    private BigDecimal withdrewcash;

    /**
     * 冻结中金额
     */
    private BigDecimal frozencash;

    /**
     * 积分
     */
    private Long score;

    /**
     * 是否完善资料
     */
    private Boolean isinfocompleted;

    /**
     * 所属酒店ID
     */
    private Integer hotelid;

    /**
     * 管理员ID
     */
    private Integer adminid;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 1:个人，2:管理员
     */
    private Byte roletype;

    /**
     * 会员等级
     */
    private Byte userlevel;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 修改日期
     */
    private Integer updatedate;

}