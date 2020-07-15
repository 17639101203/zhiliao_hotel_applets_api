package com.zhiliao.hotel.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author null
 * @date 2020-06-06
 */
@Data
public class ZlRentCarGoods implements Serializable {
    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 商品图片
     */
    private String coverimgurl;

    /**
     * 租车车型号
     */
    private String goodsname;

    /**
     * 车牌号
     */
    private String carnumber;

    /**
     * 品牌等标签“|”隔开
     */
    private String tags;

    /**
     * 租金/时
     */
    private BigDecimal rentprice;

    /**
     * 库存
     */
    private Integer stockcount;

    /**
     *
     */
    private Integer realtimestockcount;

    /**
     * 可取消订单时间(分钟)
     */
    private Integer cancancelorderminute;

    /**
     * 0:下架;1上架
     */
    private Byte goodsstatus;

    /**
     * 审核状态:-1拒绝;0待提交;1待审核;2审核通过
     */
    private Byte checkstatus;

    /**
     * 排序
     */
    private Integer sort;

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
     * 租车详情
     */
    private String content;

    /**
     * 租车协议文件地址
     */
    private String url;

}