package com.zhiliao.hotel.controller.myInfo.vo;

/**
 *
 */
public class MyInfoReturn{
    
    /**
     * 微信昵称
     */
    private String nickname;
    
    /**
     * 头像地址
     */
    private String headimgurl;
    
    /**
     * 待付款数量
     */
    private Long waitForPay;
    
    /**
     * 待收货数量
     */
    private Long waitForGoods;
    
    /**
     * 全部订单数量
     */
    private Long allOrder;
    
    /**
     * 待评价
     */
    private Long appraise;
    
    /**
     * 优惠券
     */
    private Integer coupons;
    
    /**
     * 客服电话
     */
    private String serviceTel;
    
    public String getNickname(){
        return nickname;
    }
    
    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    
    public String getHeadimgurl(){
        return headimgurl;
    }
    
    public void setHeadimgurl(String headimgurl){
        this.headimgurl=headimgurl;
    }
    
    public Long getWaitForPay(){
        return waitForPay;
    }
    
    public void setWaitForPay(Long waitForPay){
        this.waitForPay=waitForPay;
    }
    
    public Long getWaitForGoods(){
        return waitForGoods;
    }
    
    public void setWaitForGoods(Long waitForGoods){
        this.waitForGoods=waitForGoods;
    }
    
    public Long getAllOrder(){
        return allOrder;
    }
    
    public void setAllOrder(Long allOrder){
        this.allOrder=allOrder;
    }
    
    public Long getAppraise(){
        return appraise;
    }
    
    public void setAppraise(Long appraise){
        this.appraise=appraise;
    }
    
    public Integer getCoupons(){
        return coupons;
    }
    
    public void setCoupons(Integer coupons){
        this.coupons=coupons;
    }
    
    public String getServiceTel(){
        return serviceTel;
    }
    
    public void setServiceTel(String serviceTel){
        this.serviceTel=serviceTel;
    }
    
    @Override
    public String toString(){
        return "MyInfoReturn{"+
                "nickname='"+nickname+'\''+
                ", headimgurl='"+headimgurl+'\''+
                ", waitForPay="+waitForPay+
                ", waitForGoods="+waitForGoods+
                ", allOrder="+allOrder+
                ", appraise="+appraise+
                ", coupons="+coupons+
                ", serviceTel='"+serviceTel+'\''+
                '}';
    }
    
}
