package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.zlRepairorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZlRepairorderMyMapper {


    List<zlRepairorder> findAllByUserId(@Param("userId") Long userId, @Param("orderstatus") Integer orderstatus);

    zlRepairorder findDetail(@Param("orderID") Long orderID);

}
