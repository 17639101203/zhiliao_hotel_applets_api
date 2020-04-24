package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderMapper extends Mapper<ZlOrder>{
   
   List<ZlOrder> findAllOrder(@Param("userID") Long userID, @Param("orderType") Integer orderType, @Param("orderStatus") Integer orderStatus, @Param("payStatus") Integer payStatus, @Param("payType") Integer payType);
    
    //取消订单
    void byOrderId(ZlOrder order);
    
    ZlOrder findById(@Param("orderid") Long orderid);


    void insertOrder(ZlOrder zlOrder);

}
