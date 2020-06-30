package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 客房服务订单表
 *
 * @author gaoxiang
 * @date 2020-05-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "zl_serviceorder")
public class ZlServiceorder implements Serializable {
    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(generator="JDBC")
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户
     */
    private String username;

    /**
     * 用户电话
     */
    private String tel;

    /**
     * 订单编号
     */
    private String serialnumber;

    /**
     * 服务项目
     */
    private String serviceitem;

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
     * 第一张商品图片地址
     */
    private String goodscoverurl;

    /**
     * 楼层号
     */
    private String floornumber;

    /**
     * 房间号
     */
    private String roomnumber;

    /**
     * 来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
     */
    private Integer comeformid;

    /**
     * 预约时间
     */
    private Integer bookdate;

    /**
     * 超时时间
     */
    private Integer timeoutdate;

    /**
     * 其它需求备注
     */
    private String remark;

    /**
     * -1:已取消;0待配送;1已完成
     */
    private Byte orderstatus;

    /**
     * 操作人员
     */
    private String operatorname;

    /**
     * 操作人员IP
     */
    private String operatorip;

    /**
     * 操作人员备注
     */
    private String operatorremark;

    /**
     * 送达时间;默认0表示尽快送达
     */
    private Integer deliverydate;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;
}