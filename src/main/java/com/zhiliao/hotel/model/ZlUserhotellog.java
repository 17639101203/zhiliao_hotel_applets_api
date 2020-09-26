package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author null
 * @date 2020-09-22
 */
@Data
@Table(name = "zl_userhotellog")
public class ZlUserhotellog implements Serializable {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户id
     */
    private Long userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 酒店id
     */
    private Integer hotelid;

    /**
     * 酒店名称
     */
    private String hotelname;

    /**
     * 开始时间
     */
    private Integer starttime;

    /**
     * 结束时间
     */
    private Integer endtime;

    /**
     * 创建时间
     */
    private Integer createdate;

}