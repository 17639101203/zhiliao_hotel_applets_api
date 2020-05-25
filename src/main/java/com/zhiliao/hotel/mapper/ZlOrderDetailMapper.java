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
    
//    void byOrderdetailId(ZlOrder order);

//    void insertOrderDetail(List<ZlOrderDetail> zlOrderDetailList);
//    void insertOrderDetail(ZlOrderDetail zlOrderDetail);


}
