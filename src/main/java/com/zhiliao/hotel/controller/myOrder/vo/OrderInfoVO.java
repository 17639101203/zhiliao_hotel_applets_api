package com.zhiliao.hotel.controller.myOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
@ApiModel("查询订单")
public class OrderInfoVO{
    
    
    @ApiModelProperty(value="用户ID", hidden=true)
    private Long userId;
    
    /**
     * 支付方式：0无支付方式；1微信；2支付宝；3银行卡；4挂账。
     */
    @ApiModelProperty(value="支付方式：0无支付方式；1微信；2支付宝；3银行卡；4挂账。", allowableValues="0,1,2,3,4")
    private Byte paytype;
    
    /**
     * 支付状态：0无须支付；1：待支付；2已支付。
     */
    @ApiModelProperty(value="支付状态：0无须支付；1：待支付；2已支付。", allowableValues="0,1,2")
    private Byte paystatus;
    
    /**
     * 订单状态：-2报损；-1已取消；0待确认；1已确认/已下单；2已发货；3已签收/已完成；4最终完成(不能被操作)。
     */
    @ApiModelProperty(value="订单状态：-2报损；-1已取消；0待确认；1已确认/已下单；2已发货；3已签收/已完成；4最终完成(不能被操作)。", allowableValues="-2,-1,0,1,2,3,4")
    private Byte orderstatus;
    
    /**
     * 退款状态：-2退款被驳回；-1用户取消退款；1审核中；2退款中；3已退款。
     */
    @ApiModelProperty(value="退款状态：-2退款被驳回；-1用户取消退款；1审核中；2退款中；3已退款。", allowableValues="-2,-1,1,2,3")
    private Byte refundstatus;
    
    /**
     * 所属模块： 1便利店；2餐饮服务；3情趣用品；4土特产。
     */
    @ApiModelProperty(value="所属模块：1便利店；2餐饮服务；3情趣用品；4土特产。", allowableValues="1,2,3,4")
    private Short belongmodule;
    
    /**
     * 页码
     */
    @ApiModelProperty(value="页码", required=true)
    private Integer pageNo;
    
    /**
     * 每页条数
     */
    @ApiModelProperty(value="每页条数", required=true)
    private Integer pageSize;
    
    public Long getUserId(){
        return userId;
    }
    
    public Byte getPaytype(){
        return paytype;
    }
    
    public Byte getPaystatus(){
        return paystatus;
    }
    
    public Byte getOrderstatus(){
        return orderstatus;
    }
    
    public Byte getRefundstatus(){
        return refundstatus;
    }
    
    public Short getBelongmodule(){
        return belongmodule;
    }
    
    public Integer getPageNo(){
        return pageNo;
    }
    
    public Integer getPageSize(){
        return pageSize;
    }
    
    public void setUserId(Long userId){
        this.userId=userId;
    }
    
    @Override
    public String toString(){
        return "OrderInfoVO{"+
                "userId="+userId+
                ", paytype="+paytype+
                ", paystatus="+paystatus+
                ", orderstatus="+orderstatus+
                ", refundstatus="+refundstatus+
                ", belongmodule="+belongmodule+
                ", pageNo="+pageNo+
                ", pageSize="+pageSize+
                '}';
    }
    
}
