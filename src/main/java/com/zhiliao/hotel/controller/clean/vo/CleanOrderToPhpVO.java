package com.zhiliao.hotel.controller.clean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 10:48
 **/
@Data
public class CleanOrderToPhpVO {

    private Long OrderID;//订单ID
    private Long UserID;//用户ID
    private String UserName;//用户名
    private String Tel;//用户电话
    private String SerialNumber;//订单编号
    private Integer HotelID;//酒店ID
    private String HotelName;//酒店名
    private String FloorNumber;//楼层数
    private Integer RoomID;//房间ID
    private String RoomNumber;//房间号
    private Integer ComeFormID;//来自:0后台;1小程序C端;2小程序B端;3公众号;4民宿;5好评返现;6分时酒店
    private Integer BookDate;//预定清扫时间
    private Integer TimeoutDate;//超时时间
    private String Remark;//其它需求备注
    private Byte OrderStatus;//-1:已取消;0待清扫;1已完成
    private String OperatorName;//操作人员
    private String OperatorIP;//操作人员IP
    private String OperatorRemark;//操作人员备注
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Integer CreateDate;//下单时间
    private Integer UpdateDate;//支付/取消时间

}
