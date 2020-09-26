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
@Table(name = "zl_usermenulog")
public class ZlUsermenulog implements Serializable {
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
     * 菜单id
     */
    private Integer menuid;

    /**
     * 菜单父id
     */
    private Integer parentid;

    /**
     * 菜单名称
     */
    private String menuname;

    /**
     * Url地址
     */
    private String linkurl;

    /**
     * 图标
     */
    private String iconurl;

    /**
     * 创建时间
     */
    private Integer createdate;

}