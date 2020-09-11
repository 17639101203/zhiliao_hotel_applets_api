package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 客房信息
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_hotelroom")
public class ZlHotelroom implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer roomid;

    /**
     * 房型ID
     */
    private Integer roomtypeid;

    /**
     * 所属酒店ID
     */
    private Integer hotelid;

    /**
     * 客房楼层
     */
    private String roomfloor;

    /**
     * 客房编号
     */
    private String roomnumber;

    /**
     * 房型
     */
    private String model;

    /**
     * wifi名称
     */
    private String wifiname;

    /**
     * wifi密码
     */
    private String wifipwd;

    /**
     * 是否已绑定，0未绑定，1已绑定
     */
    private Byte roomstatus;

    /**
     * 二维码编号
     */
    private String serialnumber;

    /**
     * 是否是房间 0前台;1房间
     */
    private Integer roomTypeFlag;

    /**
     * 删除状态:0正常;1删除;
     */
    private String isdelete;

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
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 创建时间
     */
    private Integer createdate;

    /**
     * 旧ID
     */
    private Integer oldid;

}
