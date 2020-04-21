package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;

public interface ZlRepairorderMapper {

    Integer insertRepairorder(ZlRepairorder repairorder);

    /**
     * 根据用户名查询订单生成时间最近的订单
     * @param UserID
     * @return
     */
    ZlRepairorder queryByUserIdDescByOrderId(Long UserID);
}
