package com.zhiliao.hotel.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "zl_serviceorder")
public class ZlServiceorder implements Serializable {
    /**
     * 订单ID
     */
    private Long orderid;

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
     * 来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Integer comeformid;

    /**
     * 其它需求备注
     */
    private String remark;

    /**
     * -1:已取消;0等待确认;1已确认;2已处理
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
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    public ZlServiceorder(Long userid, String serialnumber, Integer hotelid,String hotelname,
                          Integer roomid, String roomnumber, Integer comeformid, String remark,
                          Integer createdate, Integer updatedate){
        this.userid = userid;
        this.serialnumber = serialnumber;
        this.hotelid = hotelid;
        this.hotelname = hotelname;
        this.roomid = roomid;
        this.roomnumber = roomnumber;
        this.comeformid = comeformid;
        this.remark = remark;
        this.createdate = createdate;
        this.updatedate = updatedate;
    }

}