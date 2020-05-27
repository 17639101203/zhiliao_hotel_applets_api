package com.zhiliao.hotel.common.constant;

/**
 * @author chenrong
 * @created date 2020/4/11
 */
public class RedisKeyConstant {

    //banner key
    public final static String BANNER_KEY = "banner";

    //存入redis的库存的键名称,后加SkuID
    public final static String ORDER_HOTELGOODSSKUID_ID = "order_HotelGoodsSkuID_";

    //存入redis的优惠券的键的名称,后加RecID
    public final static String ORDER_RECID = "order_RecID_";
    //存入redis的优惠券集合的名称,后加OrderSerialNo
    public final static String ORDER_RECID_ORDERSERIALNO = "order_RecID_OrderSerialNo_";

    //存入redis的用户订单信息的键的名称,后加OrderSerialNo
    public final static String ORDER_ORDERSERIALNO = "order_OrderSerialNo_";
    //存入redis的用户订单信息的标记的名称,后加OrderSerialNo
    public final static String ORDER_ORDERSERIALNO_FLAG = "order_OrderSerialNo_Flag_";

}
