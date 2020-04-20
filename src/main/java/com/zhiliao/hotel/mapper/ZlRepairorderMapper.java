package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;

public interface ZlRepairorderMapper {

    public Integer insertRepairorder(ZlRepairorder repairorder);



    /**
     * 根据用户名查询订单生成时间最近的订单
     * @param UserID
     * @return
     */
    public ZlRepairorder queryByUserIdDescByOrderId(Long UserID);
}
