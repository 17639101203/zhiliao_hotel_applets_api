package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlServiceorderdetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ZlServiceorderdetailMapper extends Mapper<ZlServiceorderdetail> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_serviceorderdetail
     *
     * @mbg.generated Sat May 23 14:48:17 CST 2020
     */
//    int insert(ZlServiceorderdetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zl_serviceorderdetail
     *
     * @mbg.generated Sat May 23 14:48:17 CST 2020
     */
//    int insertSelective(ZlServiceorderdetail record);

    //获取订单商品当日购买次数
    List<ZlServiceorderdetail> getBuyNumByOrderIds(@Param("goodsId") String goodsId, @Param("hotleId") Integer hotleId
            , @Param("roomID") Integer roomID, @Param("start") Integer start, @Param("end") Integer end);

    //插入多条订单详情表
    int insertOrderDetailList(@Param("zlServiceorderdetails") List<ZlServiceorderdetail> zlServiceorderdetails);

    List<ZlServiceorderdetail> findByOrderId(@Param("orderId") Long orderId);
}