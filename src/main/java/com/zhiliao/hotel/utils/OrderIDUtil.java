package com.zhiliao.hotel.utils;

public class OrderIDUtil {


    /**
     * 生成订单编号
     *
     * @param type 业务类型 例： 清扫  QS
     * @return
     */
    public static String createOrderID(String type) {

        IdWorker idWorker = new IdWorker();
        String s = idWorker.nextId() + "";
        return type + s;
    }

    public static void main(String[] args) {
        String orderID = createOrderID("BL");
        System.out.println(orderID);
    }
}