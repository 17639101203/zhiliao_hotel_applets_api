package com.zhiliao.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品表
 *
 * @author xiehuiyi
 * @date 2020-04-15
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zl_goods")
public class ZlGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Integer goodsid;

    /**
     * 商品编号
     */
    private String goodsnumber;

    /**
     * 所属分类ID
     */
    private Integer goodscategoryid;

    /**
     * 供应商ID
     */
    private Integer supplierid;

    /**
     * 商品封面图片
     */
    private String coverimgurl;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否推荐:0:否;1:是
     */
    private Boolean isrecommand;

    /**
     * -1:紧急下架;0:安全下架;1上架;2导入待编辑
     */
    private Byte goodsstatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isdelete;

    /**
     * 总库存
     */
    private Integer totalstockcount;

    /**
     * 总销量
     */
    private Integer totalsoldcount;

    /**
     * 总虚拟销量
     */
    private Integer totalvirtualsoldcount;

    /**
     * 即时库存(扣除供应给酒店后的库存)
     */
    private Integer realtimestockcount;

    /**
     * 原价
     */
    private BigDecimal originalprice;

    /**
     * 现价
     */
    private BigDecimal currentprice;

    /**
     * 总访问量
     */
    private Integer totalvisitcount;

    /**
     * 添加时间
     */
    private Integer createdate;

    /**
     * 修改时间
     */
    private Integer updatedate;

    /**
     * 审核状态:-1驳回;0待审核;1审核中;2审核通过
     */
    private Byte checkstatus;

    /**
     * 操作备注
     */
    private String operatorremark;

    /**
     * 所属模块: 1便利店;2餐饮服务;3情趣用品;4土特产
     */
    private Short belongmodule;

    /**
     * 操作人员
     */
    private String operatorusername;

    /**
     * 操作人员IP
     */
    private String operatorip;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 生产商
     */
    private String producer;

    /**
     * 由谁配送：1酒店配送，2供应商配送
     */
    private Byte sendtype;

    /**
     * 商品详情
     */
    private String content;

    /**
     * 商品上架时间
     */
    private Integer update;

    /**
     * 商品下架时间
     */
    private Integer downdate;

}