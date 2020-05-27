package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlOrder;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderDetailMapper extends Mapper<ZlOrderDetail>{
    
    ZlOrderDetail findOrder(@Param("userID") Long userID, @Param("orderID") Long orderID);
    
    List<ZlOrderDetail> findGoods(@Param("userID") Long userID, @Param("belongModule") Short BelongModule);
    
    Long countGoods(@Param("userID") Long userID, @Param("belongModule") Short BelongModule);

    void updateOrderDetailUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("belongModule") Integer belongModule, @Param("updateDate") Integer updateDate);

}
