package com.zhiliao.hotel.controller.hotelfacility.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 14:23
 **/
@Data
public class ZlHotelFacilityOrderToPhpVO {

    private Long OrderID;//订单ID
    private Integer FacilityID;//设施ID
    private Long UserID;//用户ID
    private String SerialNumber;//订单编号
    private Integer HotelID;//酒店ID
    private String HotelName;//酒店名
    private Integer RoomID;//房间ID
    private String RoomNumber;//房间号
    private String FacilityName;//设施名称
    private String CoverUrl;//设施封面图片地址
    private String Remark;//备注信息
    private Double TotalPrice;//总价
    private Double ActuallyPay;//实际支付金额
    private Byte PayType;//支付方式0: 无支付方式;1微信;2支付宝;3银行卡;4其它
    private Integer ComeFormID;//来自1小程序C端，2小程序B端，3公众号,4民宿，5好评返现，6分时酒店
    private Integer UseBeginDate;//开始时间
    private Integer UseEndDate;//结束时间
    private Byte PayStatus;//0无须支付;1:待支付;2已支付
    private Byte OrderStatus;//-1:已取消;0等待确认;1已确认
    private String OperatorName;//后台操作人
    private String OperatorIP;//操作人IP
    private String OperatorRemark;//操作人备注
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Boolean IsUserDelete;//用户删除:0否;1是
    private Integer CreateDate;//创建时间
    private Integer UpdateDate;//修改时间
    
}
