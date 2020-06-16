package com.zhiliao.hotel.controller.hotel.vo;

import com.zhiliao.hotel.controller.goods.vo.BusinessHoursVO;
import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-06-16 11:47
 **/
@Data
public class ZlXcxmenuVO {

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
    private Integer parentId;

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
    private Integer serviceTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态:-1禁用;1启用
     */
    private Boolean menustatus;

    /**
     *
     */
    private Integer position;

    /**
     * 小程序渠道：1C端，2B端
     */
    private Byte comefromid;

    /**
     * 删除状态:0正常;1删除;
     */
    private Integer isdelete;

    /**
     * 添加时间
     */
    private Integer createdate;


    private Integer isFrontendShow;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 营业时间
     */
    private BusinessHoursVO businessHoursVO;

}
