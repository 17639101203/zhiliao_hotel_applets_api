package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlCleanOrder;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;



public interface ZlCleanOrderMapper extends Mapper<ZlCleanOrder> {

    Integer addCleanOrder(ZlCleanOrder zlCleanOrder);

    Map<String,Object> selectCleanDetails(@Param("userid") Long userid, @Param("serialnumber") String serialnumber);

    void removeCleanOrder(@Param("userid") Long userid, @Param("serialnumber") String serialnumber,
                          @Param("updatedate") Integer updatedate);
}