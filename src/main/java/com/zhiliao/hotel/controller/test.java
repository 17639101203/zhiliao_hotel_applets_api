package com.zhiliao.hotel.controller;

import java.util.LinkedList;

/**
 * @program: zhiliao-hotel-applets-api
 * @description
 * @author: Mr.xu
 * @create: 2020-05-26 11:31
 **/
public class test {
    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");

        System.out.println(strings);

        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));

        }
    }
}
