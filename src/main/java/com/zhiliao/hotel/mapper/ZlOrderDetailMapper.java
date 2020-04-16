package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 */
public interface ZlOrderDetailMapper extends Mapper<ZlOrderDetail>{
    
    ZlOrderDetail findOrder(@Param("userID") Long userID,@Param("orderID") Long orderID);
    
    void byOrderdetailId(ZlOrder order);
    
}