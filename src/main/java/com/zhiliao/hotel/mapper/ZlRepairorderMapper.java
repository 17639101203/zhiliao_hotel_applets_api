package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.zlRepairorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlRepairorderMapper {

    public Integer insertRepairorder(zlRepairorder repairorder);

    List<zlRepairorder> findAllByUserId(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);

    zlRepairorder findDetail(@Param("orderID") Long orderID);

}
