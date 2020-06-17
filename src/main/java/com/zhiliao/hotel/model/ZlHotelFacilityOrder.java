package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author null
 * @date 2020-04-28
 */
@Table(name = "zl_hotelfacilityorder")
@Data
public class  ZlHotelFacilityOrder implements Serializable {
    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long orderid;

    /**
     * 设施ID
     */
    private Integer facilityid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 订单编号
     */
    private String serialnumber;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 酒店名
     */
    private String hotelname;

    /**
     * 房间ID
     */
    private Integer roomid;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 设施名称
     */
    private String facilityname;

    /**
     * 设施封面图片地址
     */
    private String coverurl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 总价
     */
    private BigDecimal totalprice;

    /**
     * 实际支付金额
     */
    private BigDecimal actuallypay;

    /**
     * 支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它
     */
    private Byte paytype;

    /**
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 开始时间
     */
    private Integer usebegindate;

    /**
     * 结束时间
     */
    private Integer useenddate;

    /**
     * 0无须支付;1:待支付;2已支付
     */
    private Byte paystatus;

    /**
     * -1:已取消;0等待确认;1已确认
     */
    private Byte orderstatus;

    /**
     * 后台操作人
     */
    private String operatorname;

    /**
     * 操作人IP
     */
    private String operatorip;

    /**
     * 操作人备注
     */
    private String operatorremark;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

}