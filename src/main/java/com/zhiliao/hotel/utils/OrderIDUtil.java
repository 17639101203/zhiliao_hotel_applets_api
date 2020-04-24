package com.zhiliao.hotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderIDUtil {


    /**
     * 生成订单ID
     *
     * @param type 业务类型 例： 清扫  QS
     * @return
     */
    public static String createOrderID(String type) {
        // 获取当前日期年月日yyyyMMdd
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String currentDate = format.format(date);
        // 截取后五位时间戳
        String time = String.valueOf(date.getTime());
        time = time.substring(time.length() - 5);
        // 生成五位随机数
        int random = (int) ((Math.random() * 9 + 1) * 10000);
        return type + currentDate + time + random;
    }

    public static void main(String[] args) {
        String orderID = createOrderID("BL");
        System.out.println(orderID);
    }
}