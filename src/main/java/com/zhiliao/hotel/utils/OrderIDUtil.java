package com.zhiliao.hotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderIDUtil {


    /**
     * 生成订单编号
     *
     * @param type 业务类型 例： 清扫  QS
     * @return
     */
    public static String createOrderID(String type) {
        // 获取当前日期年月日时分yyyyMMddHHmm
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String currentDate = format.format(date);
        /*// 截取后五位时间戳
        String time = String.valueOf(date.getTime());
        time = time.substring(time.length() - 4);*/
        // 生成五位随机数
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        return type + currentDate + random;
    }

    public static void main(String[] args) {
        String orderID = createOrderID("BL");
        System.out.println(orderID);
    }
}