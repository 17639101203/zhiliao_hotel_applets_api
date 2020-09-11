package com.zhiliao.hotel.model;

import com.zhiliao.hotel.controller.goods.vo.BusinessHoursVO;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 酒店小程序菜单
 *
 * @author denghanchen
 * @date 2020-04-14
 */
@Data
@Table(name = "zl_xcxmenu")
public class ZlXcxmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营业时间
     */
    @Transient
    private BusinessHoursVO businessHoursVO;

    /**
     * 菜单栏ID
     */
    private Integer menuid;


    /**
     * 酒店ID:为0时表示平台;
     */
    private Integer hotelid;

    /**
     * 父Id
     */
    private Integer parentid;

    /**
     * 功能菜单名称
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
     * 0:无;1有
     */
    private Boolean havebanner;

    /**
     * 服务时间段
     */
    private String serviceopentime;


    /**
     * 服务时间(超时时间－分钟)
     */
    private Integer servicetime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态:-1禁用;1启用
     */
    private Boolean menustatus;

    /**
     * ComeFormID:来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
     */
    private Byte comefromid;

    /**
     * 是否商业模块：0否；1是；
     */
    private Byte iscommerce;

    /**
     * 是否前台显示：0否；1是；
     */
    private Byte isfrontendshow;

    /**
     * 操作员
     */
    private String operatorname;

    /**
     * 操作员IP
     */
    private String operatorip;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 菜单位置 1:顶部 2:中部
     */
    private Integer position;

}