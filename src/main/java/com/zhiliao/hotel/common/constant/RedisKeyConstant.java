package com.zhiliao.hotel.common.constant;

/**
 * @author chenrong
 * @created date 2020/4/11
 */
public class RedisKeyConstant {

    //用户登录Token存活时间(单位:秒)
    public final static Integer USERTOKENTIME = 60 * 60 * 2;

    //用户刷新Token存活时间(单位:秒)
    public final static Integer USERFLASHTIME = 60 * 60 * 24 * 30;

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

    /**
     * 酒店超市主题
     */
    public static final String TOPIC_HOTELSHOP = "TOPIC_HOTELSHOP_ORDER";
    /**
     * 客房服务主题
     */
    public static final String TOPIC_ROOMSERVICE = "TOPIC_SERVICE_ORDER";
    /**
     * 清扫订单主题
     */
    public static final String TOPIC_CLEAN = "TOPIC_CLEAN_ORDER";
    /**
     * 维修订单主题
     */
    public static final String TOPIC_REPAIR = "TOPIC_REPAIR_ORDER";
    /**
     * 设施订单主题
     */
    public static final String TOPIC_FACILITY = "TOPIC_FACILITY_ORDER";
    /**
     * 退房订单主题
     */
    public static final String TOPIC_CKECKOUT = "TOPIC_CKECKOUT_ORDER";
    /**
     * 续住订单主题
     */
    public static final String TOPIC_CONTINUE_LIVE = "TOPIC_CONTINUE_LIVE_ORDER";
    /**
     * 租车订单主题
     */
    public static final String TOPIC_RENT_CAR = "TOPIC_RENT_CAR_ORDER";
    /**
     * 叫醒订单主题
     */
    public static final String TOPIC_WAKE = "TOPIC_WAKE_ORDER";
    /**
     * 发票订单主题
     */
    public static final String TOPIC_SERVICE_GOODS = "TOPIC_SERVICE_GOODS";

}
