package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "zl_hotelroomqrcode")
public class ZlHotelRoomQrcode {

    /**
     *二维码ID
     */
    private Integer QrCodeID;

    /**
     * 二维码链接地址
     */
    private String QrCodeUrl;

    /**
     * 二维码链接地址
     */
    private String BackQrCodeUrl;

    /**
     *酒店ID
     */
    private Integer HotelID;

    /**
     *客房ID
     */
    private Integer RoomID;

    /**
     *二维码编号
     */
    private String SerialNumber;

    /**
     * 状态:-1:禁用；0:未启用；1启用;
     */
    private Integer Status;

    /**
     * 领用时间
     */
    private Integer UseDate;

    /**
     * 0: 未绑定;1已绑定
     */
    private Integer IsBind;

    /**
     * 删除状态:0正常;1删除
     */
    private Integer IsDelete;

    /**
     * 创建时间
     */
    private long CreateDate;

    /**
     * 修改时间
     */
    private long UpdateDate;

    /**
     * 酒店台卡模板ID
     */
    private long qrcoderecid;

    /**
     * 批次ID
     */
    private Integer batchid;

    /**
     * 生成类型1同步生成，2预生成
     */
    private Byte createtype;

    /**
     * 绑定日期
     */
    private long binddate;

}
