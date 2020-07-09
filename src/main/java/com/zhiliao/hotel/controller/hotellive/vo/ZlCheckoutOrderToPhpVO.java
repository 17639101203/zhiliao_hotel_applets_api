package com.zhiliao.hotel.controller.hotellive.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 14:43
 **/
@Data
public class ZlCheckoutOrderToPhpVO {

    private Long OrderID;//订单ID
    private Long UserID;//用户ID
    private String OrderSerialNo;//订单编号
    private Integer HotelID;//酒店ID
    private String HotelName;//酒店名
    private Integer RoomID;//房间ID
    private String RoomNumber;//房间号
    private String UserName;//用户名
    private String Tel;//手机
    private Byte OrderStatus;//订单状态:-1:取消退房;0等待退房;1完成退房
    private String OperatorName;//操作员
    private String OperatorIP;//操作员IP
    private String OperatorRemark;//操作员备注
    private Integer CheckOutDate;//退房时间
    private String Remark;//备注
    private Boolean IsUserDelete;//用户删除:0否;1是
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Integer CreateDate;//创建时间（下单时间)
    private Integer UpdateDate;//修改时间（操作时间）
    
}
