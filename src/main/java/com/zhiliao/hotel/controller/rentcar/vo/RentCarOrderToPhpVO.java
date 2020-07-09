package com.zhiliao.hotel.controller.rentcar.vo;

import lombok.Data;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-07-09 14:51
 **/
@Data
public class RentCarOrderToPhpVO {

    private Long OrderID;//订单ID
    private Long UserID;//用户ID
    private String OrderSerialNo;//订单编号
    private Integer HotelID;//酒店ID
    private String HotelName;//酒店名
    private Integer RoomID;//房间ID
    private String RoomNumber;//房间号
    private String UserName;//用户名
    private String Tel;//手机
    private Byte OrderStatus;//订单状态:-1:已取消;0待处理;1处理完成
    private String OperatorName;//操作员
    private String OperatorIP;//操作员IP
    private String OperatorRemark;//操作员备注
    private String GoodsName;//租车车型号
    private String CarNumber;//车牌号
    private Integer RentBeginDate;//租车开始时间
    private Integer RentEndDate;//租车结束时间
    private Integer GiveBackDate;//归还时间
    private Double RentPrice;//租金/天
    private Double RentTotalPrice;//租车费用
    private Integer CanCancelOrderMinute;//可取消订单时间(分钟)
    private String Remark;//备注
    private Boolean IsDelete;//删除状态:0正常;1删除;
    private Boolean IsUserDelete;//用户删除:0否;1是
    private Integer CreateDate;//创建时间（下单时间)
    private Integer UpdateDate;//修改时间（操作时间）

}
