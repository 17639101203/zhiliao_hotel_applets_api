package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-23 17:11
 **/
@Data
@Table(name = "zl_hoteldetail")
public class ZlHotelDetail implements Serializable {

    /**
     * 酒店详情ID
     */
    private Integer hoteldetailid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 房间数
     */
    private Integer roomcount;

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
     * 经度
     */
    private Float longitude;

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 结算方式 百分比
     */
    private BigDecimal settlement;

    /**
     * 起送金额
     */
    private BigDecimal minsendcash;

    /**
     * 配送金额
     */
    private BigDecimal sendcash;

    /**
     * 合约图片,存储多个
     */
    private String contractimgurls;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 银行卡类型（比如：中国银行）
     */
    private String bankname;

    /**
     * 开户行账号
     */
    private String bankaccountnumber;

    /**
     * 户名
     */
    private String bankaccountname;

    /**
     * 营业执照
     */
    private String bizlicenseurl;

    /**
     * 天气设置
     */
    private String weather;

    /**
     * 结算周期:1月结；2半月结；3周结;4季度结
     */
    private Byte billingcycle;

    /**
     * 保护期天数:0无保护期;在有效期内，平台不参与该酒店的销售分成
     */
    private Integer protectdays;

    /**
     * 酒店发票二维码
     */
    private String invoiceqrcodeurl;

    /**
     * 酒店发票二维码解码
     */
    private String invoiceqrcodeencodeurl;

    /**
     * 酒店公告
     */
    private String notice;

    /**
     * 租车协议
     */
    private String rentcaragreement;

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

    /**
     * 服务时间(分钟)
     */
    private Byte servicetime;

    /**
     * 开票链接
     */
    private String invoiceurl;

}
