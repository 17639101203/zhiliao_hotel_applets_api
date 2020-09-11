package com.zhiliao.hotel.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author gaoxiang
 * @date 2020-05-23
 */
@Data
@Table(name = "zl_serviceorderdetail")
public class ZlServiceorderdetail implements Serializable {
    /**
     * 自增ID
     */
    private Long orderdetailid;

    /**
     * 订单ID
     */
    private Long orderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 酒店商品ID
     */
    private Integer goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 商品图片
     */
    private String goodscoverurl;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer goodscount;

    /**
     * 酒店ID
     */
    private Integer hotelid;

    /**
     * 房间ID
     */
    private Integer roomid;

    /**
     * 删除状态:0正常;1删除;2用户删除(用户端不显示)
     */
    private Boolean isdelete;

    /**
     * 用户删除:0否;1是
     */
    private Boolean isuserdelete;

    /**
     * 下单时间
     */
    private Integer createdate;

    /**
     * 支付/取消时间
     */
    private Integer updatedate;

    @Transient
    private Integer buyNum;
}