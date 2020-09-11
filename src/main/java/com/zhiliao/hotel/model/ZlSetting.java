package com.zhiliao.hotel.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 平台设置表
 *
 * @author null
 * @date 2020-08-12
 */
@Data
@Table(name = "zl_setting")
public class ZlSetting implements Serializable {
    /**
     * 设置ID
     */
    private Integer settingid;

    /**
     * 键
     */
    private String settingkey;

    /**
     * 说明
     */
    private String settingremark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 更新日期
     */
    private Integer updatedate;

    /**
     * 值
     */
    private String settingvalue;

}