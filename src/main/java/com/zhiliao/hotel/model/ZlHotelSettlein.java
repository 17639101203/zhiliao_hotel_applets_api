package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * s2加盟酒店/自主入驻酒店
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_hotelsettlein")
public class ZlHotelSettlein implements Serializable {
    /**
     * 加盟ID
     */
    private Integer settleinid;

    /**
     * 入驻成功后回填的酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名称
     */
    private String hotelname;

    /**
     * 酒店简称
     */
    private String hotelnameabbr;

    /**
     * 1：酒店自己入驻；2：通过S2入驻
     */
    private Byte jointype;

    /**
     * PartnerType=0表示用户ID，1：表示合伙人ID
     */
    private Integer extid;

    /**
     * 所属入驻的用户、合伙人
     */
    private String joinusername;

    /**
     * 联系人
     */
    private String contactname;

    /**
     * 房间数
     */
    private Integer roomcount;

    /**
     * 酒店图片
     */
    private String coverurl;

    /**
     * 入驻人电话
     */
    private String joinusertel;

    /**
     * 酒店客服电话, 多个用|隔开
     */
    private String tel;

    /**
     * 酒店核对人电话(也是联系人电话)
     */
    private String mobilephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 省编号(对应area表AreaID)
     */
    private Short provincecode;

    /**
     * 市编号(对应area表)
     */
    private Short citycode;

    /**
     * 区编号(对应area表)
     */
    private Short districtcode;

    /**
     * 酒店详细地址
     */
    private String address;

    /**
     * 类型:1酒店;2民宿
     */
    private Byte hoteltype;

    /**
     * 身份证号码
     */
    private String idnumber;

    /**
     * 身份证照，多张用|隔开
     */
    private String certphotourl;

    /**
     * 手持证件照
     */
    private String liftcertphotourl;

    /**
     * 营业执照图片
     */
    private String bizlicenseurl;

    /**
     * 审核状态:1.待审核2.撤销审核,3.拒绝;4审核通过
     */
    private Byte checkstatus;

    /**
     * 酒店状态:0下架;1上架；2待上架
     */
    private Byte hotelstatus;

    /**
     * 入驻酒店合作状态：-1终止合作;0未入驻(未核对)；1已正常入驻(已核对)
     */
    private Byte settleinstatus;

    /**
     * 结算周期:1月结；2半月结；3周结;4季度结
     */
    private Byte billingcycle;

    /**
     * 保护期天数:0无保护期;在有效期内，平台不参与该酒店的销售分成
     */
    private Integer protectdays;

    /**
     * 操作人员
     */
    private String operatorusername;

    /**
     * 操作人员IP
     */
    private String operatorip;

    /**
     * 操作备注
     */
    private String operatorremark;

    /**
     * 用酒店码核对后创建的管理员名
     */
    private String adminname;

    /**
     * 酒店名额码
     */
    private String codename;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 更新日期
     */
    private Integer updatedate;

    /**
     * 添加日期
     */
    private Integer createdate;

}