package com.zhiliao.hotel.controller.myOrder.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api
 * @description
 * @author: 姬慧慧
 * @create: 2020-05-21 14:32
 **/
@Data
public class UserGoodsReturn<T> {

    private List<T> userGoodsInfoList;

    /**
     * 提示内容
     */
    private String content;

    /**
     * -1表示有商品商品库存不足,0表示提交成功
     */
    private Integer flag;

}
