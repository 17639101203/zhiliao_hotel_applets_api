package com.zhiliao.hotel.controller.myOrder.vo;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-21 14:32
 **/
public class UserGoodsReturn<T> {

    private List<T> userGoodsInfoList;

    private String content;

    public List<T> getUserGoodsInfoList() {
        return userGoodsInfoList;
    }

    public void setUserGoodsInfoList(List<T> usergoodsInfoList) {
        this.userGoodsInfoList = usergoodsInfoList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
