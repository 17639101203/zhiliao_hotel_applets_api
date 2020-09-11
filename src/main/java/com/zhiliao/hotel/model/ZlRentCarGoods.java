package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author null
 * @date 2020-06-06
 */
@Data
@Table(name = "zl_rentcargoods")
public class ZlRentCarGoods implements Serializable {
    /**
     * 商品ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
     * 租车详情
     */
    private String content;

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
     * 操作员
     */
    private String operatorname;

    /**
     * 操作员IP
     */
    private String operatorip;

    /**
     * 操作员备注
     */
    private String operatorremark;

    /**
     * 审核时间
     */
    private Integer operatime;

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
     * 租车协议文件地址
     */
//    @Transient
    private String url;

}