package com.zhiliao.hotel.service;

import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.model.ZlCleanOrder;

import java.util.List;

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
