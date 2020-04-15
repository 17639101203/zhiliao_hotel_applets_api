package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCleanOrder;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by xiegege on 2019/10/14.
 */

public interface ZlCleanOrderMapper extends Mapper<ZlCleanOrder> {

    int addCleanOrder(ZlCleanOrder zlCleanOrder);
}