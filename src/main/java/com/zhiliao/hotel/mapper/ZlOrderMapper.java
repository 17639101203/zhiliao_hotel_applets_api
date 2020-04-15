package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderMapper extends Mapper<ZlOrder>{
    
    List<ZlOrder> findAllOrder(@Param("userID") Long userID);
    
}
