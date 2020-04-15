package com.zhiliao.hotel.utils;


import java.util.UUID;


public class OrderIDUtil {


    /**
     * 生成订单ID
     * @param type  业务类型 例： 清扫  QS
     * @return
     */
    public static String CreateOrderID(String type) {
        // 获取当前日期年月日
        String currentDate = DateUtils.getDateByString().replaceAll("-","");
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);//把uuid生成的"-"去掉,并保留前六位
        String OrderID;
        // 取出redis中是否有当日最大sn
        if (type!=null && type!="") {
            OrderID = type + currentDate + uuid ;
            return OrderID;
        }
           return null;
        }



}