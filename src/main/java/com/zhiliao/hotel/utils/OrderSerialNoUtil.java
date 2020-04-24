package com.zhiliao.hotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成订单编号
 */
public class OrderSerialNoUtil {

    public static void main(String[] args) {
        String num = OrderSerialNoUtil.CreateOrderSerialNo("BL");
        System.out.println(num);
    }

    /**
     * 由年月日时分秒+3位随机数
     * 生成流水号
     *
     * @return
     */
    public static String CreateOrderSerialNo(String type) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        String orderSerialNo = type + dateString + random;
        return orderSerialNo;
    }


}
