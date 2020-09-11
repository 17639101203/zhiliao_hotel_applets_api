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

    //用户登录Token存活时间(单位:秒)
    public final static Integer ACCESSTOKEN = 60 * 115;

    //banner key
    public final static String BANNER_KEY = "banner";

    //存入redis的库存的键名称,后加SkuID
    public final static String ORDER_HOTELGOODSSKUID_ID = "order_HotelGoodsSkuID_";

    //存入redis的优惠券的键的名称,后加RecID
    public final static String ORDER_COUPONUSERID = "order_couponUserId_";
    //存入redis的优惠券集合的名称,后加OrderSerialNo
    public final static String ORDER_COUPONUSERID_ORDERSERIALNO = "order_couponUserId_OrderSerialNo_";

    //存入redis的用户订单信息的键的名称,后加OrderSerialNo
    public final static String ORDER_ORDERSERIALNO = "order_OrderSerialNo_";
    //存入redis的用户订单信息的标记的名称,后加OrderSerialNo
    public final static String ORDER_ORDERSERIALNO_FLAG = "order_OrderSerialNo_Flag_";

    //存入redis的优惠券,用于优惠券过期操作数据库状态的处理,后加用户优惠券表自增id
    public final static String COUPON_USER_FLAG = "coupon_user_";

    /**
     * 便利店主题
     */
    public static final String TOPIC_STORE_ORDER = "TOPIC_STORE_ORDER";

    /**
     * 便利店主题
     */
//    public static final String TOPIC_STORE_ORDER = "TOPIC_STORE_ORDER";

    /**
     * 餐饮服务主题
     */
    public static final String TOPIC_CATERING_ORDER = "TOPIC_CATERING_ORDER";

    /**
     * 情趣用品主题
     */
    public static final String TOPIC_SEXTOY_ORDER = "TOPIC_SEXTOY_ORDER";

    /**
     * 土特产主题
     */
    public static final String TOPIC_LOCALSPECIALTY_ORDER = "TOPIC_LOCALSPECIALTY_ORDER";

    /**
     * 客房服务主题
     */
    public static final String TOPIC_ROOMSERVICE = "TOPIC_SERVICE_ORDER";
    /**
     * 清扫订单主题
     */
    public static final String TOPIC_CLEAN_ORDER = "TOPIC_CLEAN_ORDER";
    /**
     * 维修订单主题
     */
    public static final String TOPIC_REPAIR_ORDER = "TOPIC_REPAIR_ORDER";
    /**
     * 设施订单主题
     */
    public static final String TOPIC_FACILITY_ORDER = "TOPIC_FACILITY_ORDER";
    /**
     * 退房订单主题
     */
    public static final String TOPIC_CKECKOUT_ORDER = "TOPIC_CKECKOUT_ORDER";
    /**
     * 续住订单主题
     */
    public static final String TOPIC_CONTINUE_LIVE_ORDER = "TOPIC_CONTINUE_LIVE_ORDER";
    /**
     * 租车订单主题
     */
    public static final String TOPIC_RENT_CAR_ORDER = "TOPIC_RENT_CAR_ORDER";
    /**
     * 叫醒订单主题
     */
    public static final String TOPIC_WAKE_ORDER = "TOPIC_WAKE_ORDER";
    /**
     * 发票订单主题
     */
    public static final String TOPIC_INVOICE_ORDER = "TOPIC_INVOICE_ORDER";
    /**
     * 点赞吐槽主题
     */
    public static final String TOPIC_COMMENT = "TOPIC_COMMENT";
}
