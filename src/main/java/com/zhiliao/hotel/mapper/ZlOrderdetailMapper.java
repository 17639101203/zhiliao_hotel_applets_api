package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlOrderdetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderdetailMapper extends Mapper<ZlOrderdetail>{
    
    List<ZlOrderdetail> findOrder(@Param("orderID") Long orderID);
    
}
