package com.zhiliao.hotel.controller.myOrder.param;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-09 15:10
 **/
public class WxPayRefundParam {

    //签名
    private String sign;

    //订单号
    private String out_trade_no;

    //订单总金额
    private Integer total_fee;

    //退款金额
    private Integer refund_fee;

    public WxPayRefundParam() {
    }

    public WxPayRefundParam(String sign, String out_trade_no, Integer total_fee, Integer refund_fee) {
        this.sign = sign;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.refund_fee = refund_fee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public Integer getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(Integer refund_fee) {
        this.refund_fee = refund_fee;
    }
}
