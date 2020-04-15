package com.zhiliao.hotel.service;

import com.zhiliao.hotel.model.ZlCleanOrder;

/**
 * @Author: Zhangyong
 * @Date: 2020/4/14 13:52
 * @Description:
 */
public interface ZlCleanOrderService {
    /*
        点赞吐槽
     */
    public Integer addCleanOrder(ZlCleanOrder zlCleanOrder);
}
