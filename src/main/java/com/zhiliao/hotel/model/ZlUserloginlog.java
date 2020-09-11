package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 *  扫码率
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_userloginlog")
public class ZlUserloginlog implements Serializable {
    /**
     * 
     */
    private Long userloginlogid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户昵称
     */
    private String nickname;

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
     * 房间名
     */
    private String roomnumber;

    /**
     * 登录IP
     */
    private String loginip;

    /**
     * 渠道来源:
     */
    private int comefromid;

    /**
     * 创建时间(登录时间)
     */
    private Long createdate;

}