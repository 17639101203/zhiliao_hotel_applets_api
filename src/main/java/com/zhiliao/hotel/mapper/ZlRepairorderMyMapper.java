package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlRepairorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlRepairorderMyMapper {

    List<ZlRepairorder> findAllByUserId(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);

    ZlRepairorder findDetail(@Param("orderID") Long orderID);

}
