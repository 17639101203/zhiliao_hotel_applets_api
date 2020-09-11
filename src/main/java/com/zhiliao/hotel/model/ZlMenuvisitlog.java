package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zl_menuvisitlog")
public class ZlMenuvisitlog implements Serializable {
    /**
     * 记录ID
     */
    @Id
    private Long logid;

    /**
     * 酒店ID
     */
    private Integer hoteid;

    /**
     * 模块ID(菜单ID)
     */
    private Integer menuid;

    /**
     * 用户IP
     */
    private String userip;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 创建时间（点击时间）
     */
    private Integer createdate;

}
