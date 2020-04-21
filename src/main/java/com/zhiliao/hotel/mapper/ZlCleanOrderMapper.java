package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCleanOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by xiegege on 2019/10/14.
 */

public interface ZlCleanOrderMapper extends Mapper<ZlCleanOrder> {

    Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    ZlCleanOrder selectCleanDetails(@Param("userID") Long userID, @Param("serialNumber") String serialNumber);
}