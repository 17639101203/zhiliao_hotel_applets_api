package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author gaoxiang
 * @date 2020-05-25
 */
@Data
@Table(name = "zl_wxuserdetail")
public class ZlWxuserdetail implements Serializable {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long userdetailid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * Email
     */
    private String email;

    /**
     * QQ
     */
    private Integer qq;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 出生日期
     */
    private Integer birthdate;

    /**
     * 证件号码
     */
    private String idnumber;

    /**
     * 证件照片
     */
    private String certphotourl;

    /**
     * 持证照片
     */
    private String liftcertphotourl;

    /**
     * 从微信获取的用户国家
     */
    private String wxcountry;

    /**
     * 从微信获取的用户省
     */
    private String wxprovince;

    /**
     * 从微信获取的用户市
     */
    private String wxcity;

    /**
     * 从微信获取的用户地区
     */
    private String wxdistrict;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加日期
     */
    private Integer createdate;

    /**
     * 更新日期
     */
    private Integer updatedate;

}