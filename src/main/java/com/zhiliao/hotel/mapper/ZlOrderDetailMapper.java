package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.controller.myOrder.vo.OrderDetailInfoVO;
import com.zhiliao.hotel.model.ZlOrderDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface ZlOrderDetailMapper extends Mapper<ZlOrderDetail> {
    
    List<ZlOrderDetail> findOrderDetails(OrderDetailInfoVO vo);
    
    List<ZlOrderDetail> find2Goods(@Param("userID") Long userID, @Param("orderSerialNo") String orderSerialNo, @Param("belongModule") Short BelongModule);

    Long countGoods(@Param("userID") Long userID, @Param("orderSerialNo") String orderSerialNo, @Param("belongModule") Short BelongModule);

    void updateOrderDetailUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("belongModule") Integer belongModule, @Param("updateDate") Integer updateDate);

    void autoUpdateOrderDetailUpdateDate(@Param("out_trade_no") String out_trade_no, @Param("updateDate") Integer updateDate);

    void userDeleteOrder(@Param("orderSerialNo") String orderSerialNo, @Param("belongModule") Integer belongModule);
}
