package com.zhiliao.hotel.controller.Repair.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 14:11
 **/
@Data
public class RepairOrderToPhpVO {

    private Long OrderID;//订单ID
    private Long UserID;//用户ID
    private String UserName;//用户名
    private String Tel;//用户电话
    private String SerialNumber;//订单编号
    private Integer HotelID;//酒店ID
    private String HotelName;//酒店名
    private String FloorNumber;//楼层号
    private Integer RoomID;//房间ID
    private String RoomNumber;//房间号
    private String ImgUrls;// 报修图片地址,多个用 | 隔开
    private String Remark;//备注信息
    private Integer ComeFormID;//来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
    private Byte OrderStatus;//-1:已取消;0待维修;1已完成
    private String OperatorName;//操作人员
    private String OperatorIP;//操作员IP
    private String OperatorRemark;//操作人员备注
    private Integer RepairUseTime;//维修时长（分钟)
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Boolean IsUserDelete;//用户删除:0否;1是
    private Integer CreateDate;//下单时间
    private Integer UpdateDate;//支付/取消时间

}
